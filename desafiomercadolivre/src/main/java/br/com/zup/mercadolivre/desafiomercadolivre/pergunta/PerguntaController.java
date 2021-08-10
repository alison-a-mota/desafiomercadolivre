package br.com.zup.mercadolivre.desafiomercadolivre.pergunta;

import br.com.zup.mercadolivre.desafiomercadolivre.compartilhado.eviaremail.EnviarEmailService;
import br.com.zup.mercadolivre.desafiomercadolivre.compartilhado.eviaremail.EnviarEmailServiceProd;
import br.com.zup.mercadolivre.desafiomercadolivre.produto.Produto;
import br.com.zup.mercadolivre.desafiomercadolivre.produto.ProdutoRepository;
import br.com.zup.mercadolivre.desafiomercadolivre.usuario.Usuario;
import br.com.zup.mercadolivre.desafiomercadolivre.usuario.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
@RequestMapping("api/pergunta/{produtoId}")
public class PerguntaController {

    private final ProdutoRepository produtoRepository;
    private final PerguntaRepository perguntaRepository;
    private final UsuarioRepository usuarioRepository;
    private final EnviarEmailService enviarEmailService;

    public PerguntaController(ProdutoRepository produtoRepository, PerguntaRepository perguntaRepository,
                              UsuarioRepository usuarioRepository, EnviarEmailService enviarEmailService) {
        this.produtoRepository = produtoRepository;
        this.perguntaRepository = perguntaRepository;
        this.usuarioRepository = usuarioRepository;
        this.enviarEmailService = enviarEmailService;
    }

    @PostMapping
    public ResponseEntity<?> criar(@RequestBody @Valid PerguntaRequest perguntaRequest,
                                   @AuthenticationPrincipal Usuario usuarioLogado, @PathVariable Long produtoId) {
        Produto produto = produtoRepository.findById(produtoId).orElseThrow(()->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado"));
        Usuario donoDoProduto = usuarioRepository.findById(produto.getUsuario().getId()).orElseThrow(()->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));

        Pergunta pergunta = perguntaRequest.toModel(usuarioLogado, produto);
        perguntaRepository.save(pergunta);

        enviarEmailService.enviaEmailGenerico(donoDoProduto.getEmail());

        return ResponseEntity.status(HttpStatus.CREATED).build();

    }
}
