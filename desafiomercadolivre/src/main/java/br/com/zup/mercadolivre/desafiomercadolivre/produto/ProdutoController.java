package br.com.zup.mercadolivre.desafiomercadolivre.produto;

import br.com.zup.mercadolivre.desafiomercadolivre.categoria.CategoriaRepository;
import br.com.zup.mercadolivre.desafiomercadolivre.usuario.Usuario;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.Set;

@RestController
@RequestMapping("/api/produto")
public class ProdutoController {

    private final UploaderFake uploaderFake;

    private final ProdutoRepository produtoRepository;
    private final CategoriaRepository categoriaRepository;

    public ProdutoController(UploaderFake uploaderFake, ProdutoRepository produtoRepository,
                             CategoriaRepository categoriaRepository) {
        this.uploaderFake = uploaderFake;
        this.produtoRepository = produtoRepository;
        this.categoriaRepository = categoriaRepository;
    }

    @PostMapping
    public ResponseEntity<?> cria(@Valid @RequestBody ProdutoRequest produtoRequest,
                                  @AuthenticationPrincipal Usuario usuarioLogado) {

        Produto produto = produtoRequest.toModel(categoriaRepository, usuarioLogado);
        produtoRepository.save(produto);

        return ResponseEntity.ok().body(produto.toString());
    }

    @PostMapping("/{produtoId}/imagem")
    public ResponseEntity<String> novaImagem(@PathVariable Long produtoId, @Valid ImagemRequest imagemRequest,
                                             @AuthenticationPrincipal Usuario usuarioLogado) {

        //Localizando o produto
        Produto produto = produtoRepository.findById(produtoId).orElseThrow(() -> new ResponseStatusException(HttpStatus
                .NOT_FOUND, "Produto não encontrado"));

        //Validando se o produto pertence ao usuarioLogado
        if (!produto.pertenceAoUsuario(usuarioLogado)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Este produto é de outro usuário");
        }

        //Recebendo as imagens e gerando o link - aqui é onde salvamos o arquivo em algum servidor e retornamos o link.
        Set<String> links = uploaderFake.envia(imagemRequest.getImagens());

        //Associando os links com o produto
        produto.associaImagens(links);
        produtoRepository.save(produto);

        return ResponseEntity.status(HttpStatus.CREATED).body(produto.toString());

    }
}
