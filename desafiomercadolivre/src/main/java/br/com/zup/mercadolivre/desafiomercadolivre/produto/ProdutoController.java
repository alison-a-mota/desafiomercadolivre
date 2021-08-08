package br.com.zup.mercadolivre.desafiomercadolivre.produto;

import br.com.zup.mercadolivre.desafiomercadolivre.categoria.CategoriaRepository;
import br.com.zup.mercadolivre.desafiomercadolivre.opiniao.Opiniao;
import br.com.zup.mercadolivre.desafiomercadolivre.opiniao.OpiniaoRepository;
import br.com.zup.mercadolivre.desafiomercadolivre.usuario.Usuario;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/produto")
public class ProdutoController {

    private final ProdutoRepository produtoRepository;
    private final CategoriaRepository categoriaRepository;
    private final OpiniaoRepository opiniaoRepository;

    public ProdutoController(ProdutoRepository produtoRepository, CategoriaRepository categoriaRepository, OpiniaoRepository opiniaoRepository) {
        this.produtoRepository = produtoRepository;
        this.categoriaRepository = categoriaRepository;
        this.opiniaoRepository = opiniaoRepository;
    }

    @PostMapping
    public ResponseEntity<?> cria(@Valid @RequestBody ProdutoRequest produtoRequest,
                                  @AuthenticationPrincipal Usuario usuarioLogado) {

        Produto produto = produtoRequest.toModel(categoriaRepository, usuarioLogado);
        produtoRepository.save(produto);

        return ResponseEntity.ok().body(produto.toString());
    }

    @GetMapping("/{produtoId}")
    public ProdutoResponseSite listaProduto(@PathVariable Long produtoId) {

        Produto produto = produtoRepository.findById(produtoId).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado"));

        //Calculando média e quantidade de notas
        List<Opiniao> opinioes = opiniaoRepository.findAllByProdutoId(produtoId);
        var media = opinioes.stream().mapToDouble(Opiniao::getNota).average().orElse(0.0);
        var totalDeNotas = (double) opinioes.size();

        return new ProdutoResponseSite(produto, totalDeNotas, media);

    }
}
