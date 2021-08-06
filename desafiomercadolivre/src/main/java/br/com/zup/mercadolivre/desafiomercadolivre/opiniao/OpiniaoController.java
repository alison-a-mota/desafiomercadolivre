package br.com.zup.mercadolivre.desafiomercadolivre.opiniao;

import br.com.zup.mercadolivre.desafiomercadolivre.produto.Produto;
import br.com.zup.mercadolivre.desafiomercadolivre.produto.ProdutoRepository;
import br.com.zup.mercadolivre.desafiomercadolivre.usuario.Usuario;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/opiniao/{produtoId}")
public class OpiniaoController {

    private final ProdutoRepository produtoRepository;
    private final OpiniaoRepository opiniaoRepository;

    public OpiniaoController(ProdutoRepository produtoRepository, OpiniaoRepository opiniaoRepository) {
        this.produtoRepository = produtoRepository;
        this.opiniaoRepository = opiniaoRepository;
    }

    @PostMapping
    public ResponseEntity<?> cria(@RequestBody @Valid OpiniaoRequest opiniaoRequest, @PathVariable Long produtoId,
                                  @AuthenticationPrincipal Usuario usuarioLogado){

        //Validando e pegando produto
        if(produtoId==null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("O produto não foi informado");
        } else if(usuarioLogado==null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuário inválido");
        }

        Produto produto = produtoRepository.findById(produtoId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                "Produto não encontrado"));
        Opiniao opiniao = opiniaoRequest.toModel(usuarioLogado, produto);

        opiniaoRepository.save(opiniao);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
