package br.com.zup.mercadolivre.desafiomercadolivre.compra;

import br.com.zup.mercadolivre.desafiomercadolivre.compartilhado.eviaremail.EnviarEmailService;
import br.com.zup.mercadolivre.desafiomercadolivre.produto.Produto;
import br.com.zup.mercadolivre.desafiomercadolivre.produto.ProdutoRepository;
import br.com.zup.mercadolivre.desafiomercadolivre.usuario.Usuario;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
@RequestMapping("api/compra")
public class CompraController {

    private final ProdutoRepository produtoRepository;
    private final CompraRepository compraRepository;
    private final EnviarEmailService enviarEmailService;

    public CompraController(ProdutoRepository produtoRepository, CompraRepository compraRepository,
                            EnviarEmailService enviarEmailService) {
        this.produtoRepository = produtoRepository;
        this.compraRepository = compraRepository;
        this.enviarEmailService = enviarEmailService;
    }

    @PostMapping
    public ResponseEntity<String> cria(@Valid @RequestBody CompraRequest compraRequest,
                                       @AuthenticationPrincipal Usuario usuarioLogado) {

        Produto produto = produtoRepository.findById(compraRequest.getProdutoId()).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado."));

        //Valida se possui estoque suficiente
        produto.validaEstoque(compraRequest.getQuantidade());

        Compra compra = compraRequest.toModel(usuarioLogado, produto);

        compraRepository.save(compra);

        enviarEmailService.enviaEmailGenerico(produto.getUsuario().getEmail());

        return ResponseEntity
                .status(HttpStatus.FOUND)
                .location(compra.getUriRedirecionamento()).build();

    }
}
