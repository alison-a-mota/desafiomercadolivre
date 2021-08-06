package br.com.zup.mercadolivre.desafiomercadolivre.opiniao;

import br.com.zup.mercadolivre.desafiomercadolivre.produto.Produto;
import br.com.zup.mercadolivre.desafiomercadolivre.usuario.Usuario;

import javax.validation.constraints.*;

public class OpiniaoRequest {

    @NotBlank
    private String titulo;
    @Min(1)
    @Max(5)
    private int nota;
    @NotBlank
    @Size(max = 500)
    private String descricao;

    public Opiniao toModel(Usuario usuarioLogado, Produto produto) {
        return new Opiniao(this.titulo, this.nota, this.descricao, usuarioLogado, produto);
    }

    public int getNota() {
        return nota;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescricao() {
        return descricao;
    }
}
