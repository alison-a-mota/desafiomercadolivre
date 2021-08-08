package br.com.zup.mercadolivre.desafiomercadolivre.produto.imagem;

import br.com.zup.mercadolivre.desafiomercadolivre.produto.Produto;
import br.com.zup.mercadolivre.desafiomercadolivre.produto.ProdutoRepository;
import br.com.zup.mercadolivre.desafiomercadolivre.usuario.Usuario;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.Set;

@RestController
public class ImagemController {

    private final UploaderFake uploaderFake;
    private final ProdutoRepository produtoRepository;

    public ImagemController(UploaderFake uploaderFake, ProdutoRepository produtoRepository) {
        this.uploaderFake = uploaderFake;
        this.produtoRepository = produtoRepository;
    }


    @PostMapping("/api/produto/{produtoId}/imagem")
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
