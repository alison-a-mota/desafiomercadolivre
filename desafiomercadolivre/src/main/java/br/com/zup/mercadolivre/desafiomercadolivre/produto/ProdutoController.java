package br.com.zup.mercadolivre.desafiomercadolivre.produto;

import br.com.zup.mercadolivre.desafiomercadolivre.categoria.CategoriaRepository;
import br.com.zup.mercadolivre.desafiomercadolivre.usuario.Usuario;
import br.com.zup.mercadolivre.desafiomercadolivre.usuario.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
    private final UsuarioRepository usuarioRepository;

    public ProdutoController(UploaderFake uploaderFake, ProdutoRepository produtoRepository,
                             CategoriaRepository categoriaRepository, UsuarioRepository usuarioRepository) {
        this.uploaderFake = uploaderFake;
        this.produtoRepository = produtoRepository;
        this.categoriaRepository = categoriaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @PostMapping
    public ResponseEntity<?> cria(@Valid @RequestBody ProdutoRequest produtoRequest) {

        //Aqui é onde pego o usuário que está logado (tá com gambiarra ainda)
        Usuario usuario = getUsuarioLogado();

        Produto produto = produtoRequest.toModel(categoriaRepository, usuario);
        produtoRepository.save(produto);

        return ResponseEntity.ok().body(produto.toString());
    }

    @PostMapping("/{produtoId}/imagem")
    public ResponseEntity<String> novaImagem(@PathVariable Long produtoId, @Valid ImagemRequest imagemRequest) {

        //Localizar o produto
        Produto produto = produtoRepository.findById(produtoId).orElseThrow(() -> new ResponseStatusException(HttpStatus
                .NOT_FOUND, "Produto não encontrado"));

        //Aqui é onde pego o usuário que está logado (tá com gambiarra ainda)
        Usuario usuario = getUsuarioLogado();

        //Validando se o produto pertence ao usuário
        if (!produto.pertenceAoUsuario(usuario)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Este produto é de outro usuário");
        }

        //Recebendo as imagens e gerando o link - aqui é onde salvamos o arquivo em algum servidor e retornamos o link.
        Set<String> links = uploaderFake.envia(imagemRequest.getImagens());

        //Associando os links com o produto
        produto.associaImagens(links);
        produtoRepository.save(produto);

        return ResponseEntity.status(HttpStatus.CREATED).body(produto.toString());

    }

    //Método temporário para pegar o usuário logado
    public Usuario getUsuarioLogado() {
        var principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return usuarioRepository.findByEmail(((UserDetails) principal).getUsername())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "usuario não encontrado"));
    }
}
