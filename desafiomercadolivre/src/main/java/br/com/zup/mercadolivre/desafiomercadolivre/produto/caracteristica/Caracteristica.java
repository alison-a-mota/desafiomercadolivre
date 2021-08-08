package br.com.zup.mercadolivre.desafiomercadolivre.produto.caracteristica;

import br.com.zup.mercadolivre.desafiomercadolivre.produto.Produto;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
public class Caracteristica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long id;
    @NotBlank
    private String nome;
    @NotBlank
    private String descricao;
    @NotNull
    @ManyToOne
    private Produto produto;

    //Procurar sobre método Filter

    public Caracteristica(@NotBlank String nome, @NotBlank String descricao, @NotNull Produto produto) {
        this.nome = nome;
        this.descricao = descricao;
        this.produto = produto;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public Produto getProduto() {
        return produto;
    }

    @Deprecated
    public Caracteristica() {
    }
}
