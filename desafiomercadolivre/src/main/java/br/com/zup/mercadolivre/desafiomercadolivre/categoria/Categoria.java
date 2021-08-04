package br.com.zup.mercadolivre.desafiomercadolivre.categoria;

import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(unique = true)
    private String nome;
    @ManyToOne
    private Categoria categoriaMae;

    public Categoria(@NotBlank String nome, Categoria categoriaMae) {

        Assert.hasText(nome, "O nome da categoria precisa ser informado");
        Assert.notNull(categoriaMae, "É necessário informar a Categoria mãe");

        this.nome = nome;
        this.categoriaMae = categoriaMae;
    }

    public Categoria(String nome) {
        this.nome = nome;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    @Deprecated
    public Categoria() {
    }
}
