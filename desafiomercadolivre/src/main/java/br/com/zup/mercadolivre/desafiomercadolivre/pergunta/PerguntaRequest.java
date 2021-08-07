package br.com.zup.mercadolivre.desafiomercadolivre.pergunta;

import br.com.zup.mercadolivre.desafiomercadolivre.produto.Produto;
import br.com.zup.mercadolivre.desafiomercadolivre.usuario.Usuario;

import javax.validation.constraints.NotBlank;

public class PerguntaRequest {

    @NotBlank
    private String titulo;

    public Pergunta toModel(Usuario usuarioLogado, Produto produto) {
        return new Pergunta(this.titulo, usuarioLogado, produto);
    }

    public String getTitulo() {
        return titulo;
    }
}
