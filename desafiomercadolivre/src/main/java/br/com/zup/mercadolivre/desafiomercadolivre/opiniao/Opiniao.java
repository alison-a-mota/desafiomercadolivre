package br.com.zup.mercadolivre.desafiomercadolivre.opiniao;

import br.com.zup.mercadolivre.desafiomercadolivre.produto.Produto;
import br.com.zup.mercadolivre.desafiomercadolivre.usuario.Usuario;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
public class Opiniao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long id;

    @NotBlank
    private String titulo;
    @Min(1)
    @Max(5)
    private int nota;
    @NotBlank
    @Size(max = 500)
    private String descricao;

    @NotNull
    @ManyToOne
    private Usuario usuario;
    @NotNull
    @ManyToOne
    private Produto produto;

    public Opiniao(String titulo, int nota, String descricao, Usuario usuario, Produto produto) {
        this.titulo = titulo;
        this.nota = nota;
        this.descricao = descricao;
        this.usuario = usuario;
        this.produto = produto;
    }

    public String getTitulo() {
        return titulo;
    }

    public int getNota() {
        return nota;
    }

    public String getDescricao() {
        return descricao;
    }

    @Deprecated
    public Opiniao() {
    }
}
