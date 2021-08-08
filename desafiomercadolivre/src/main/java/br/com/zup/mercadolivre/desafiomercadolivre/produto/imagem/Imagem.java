package br.com.zup.mercadolivre.desafiomercadolivre.produto.imagem;

import br.com.zup.mercadolivre.desafiomercadolivre.produto.Produto;
import org.hibernate.validator.constraints.URL;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
public class Imagem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "produto_id")
    private Produto produto;
    @NotNull
    @URL
    private String link;

    public Produto getProduto() {
        return produto;
    }

    public String getLink() {
        return link;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Imagem(@NotNull @Valid Produto produto, @URL String link) {
        this.produto = produto;
        this.link = link;
    }

    @Deprecated
    public Imagem() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Imagem that = (Imagem) o;
        return Objects.equals(produto, that.produto) && Objects.equals(link, that.link);
    }

    @Override
    public int hashCode() {
        return Objects.hash(produto, link);
    }
}
