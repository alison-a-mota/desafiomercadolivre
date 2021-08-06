package br.com.zup.mercadolivre.desafiomercadolivre.produto;

import br.com.zup.mercadolivre.desafiomercadolivre.categoria.CategoriaRepository;
import br.com.zup.mercadolivre.desafiomercadolivre.compartilhado.security.UsuarioLogado;
import br.com.zup.mercadolivre.desafiomercadolivre.usuario.Usuario;
import br.com.zup.mercadolivre.desafiomercadolivre.usuario.UsuarioRepository;
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
@RequestMapping("/api/produto")
public class ProdutoController {

    private final ProdutoRepository produtoRepository;
    private final CategoriaRepository categoriaRepository;
    private final UsuarioRepository usuarioRepository;

    public ProdutoController(ProdutoRepository produtoRepository, CategoriaRepository categoriaRepository, UsuarioRepository usuarioRepository) {
        this.produtoRepository = produtoRepository;
        this.categoriaRepository = categoriaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @PostMapping
    public ResponseEntity<?> cria(@Valid @RequestBody ProdutoRequest produtoRequest, @AuthenticationPrincipal UsuarioLogado usuarioLogado) {
        //Precisa trocar para o usuário logado
        Usuario usuario = usuarioRepository.findById(produtoRequest.getUsuarioId()).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario não encontrado mané"));

        Produto produto = produtoRequest.toModel(categoriaRepository, usuario);
        produtoRepository.save(produto);

        return ResponseEntity.ok().body(produto.toString());
    }
}
