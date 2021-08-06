package br.com.zup.mercadolivre.desafiomercadolivre.produto;

import br.com.zup.mercadolivre.desafiomercadolivre.categoria.Categoria;
import br.com.zup.mercadolivre.desafiomercadolivre.usuario.Usuario;
import io.jsonwebtoken.lang.Assert;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long id;
    private final LocalDateTime instanteDeCadastro = LocalDateTime.now();

    @NotNull
    @ManyToOne
    private Categoria categoria;
    @NotNull
    @ManyToOne
    private Usuario usuario;
    @NotBlank
    private String nome;
    @NotNull
    @Positive
    private BigDecimal valor;
    @NotNull
    @PositiveOrZero
    private Long quantidade;
    @NotBlank
    @Size(max = 1000)
    @Column(length = 1000)
    private String descricao;

    @Size(min = 3)
    @Valid
    @OneToMany(mappedBy = "produto", cascade = CascadeType.PERSIST)
    private Set<Caracteristica> caracteristicas = new HashSet<>();

    public Produto(Categoria categoria, Usuario usuario, String nome, BigDecimal valor,
                   Long quantidade, String descricao, List<CaracteristicaRequest> caracteristicas) {
        this.categoria = categoria;
        this.usuario = usuario;
        this.nome = nome;
        this.valor = valor;
        this.quantidade = quantidade;
        this.descricao = descricao;

        Set<Caracteristica> novasCaracteristicas = caracteristicas.stream()
                .map(caracteristica -> caracteristica.toModel(this)).collect(Collectors.toSet());

        this.caracteristicas.addAll(novasCaracteristicas);

        Assert.isTrue(this.caracteristicas.size() >= 3, "Todo produto precisa ter no mínimo 3 características");
    }

    @Deprecated
    public Produto() {
    }

    @Override
    public String toString() {
        return "Produto{" +
                "id=" + id +
                ", instanteDeCadastro=" + instanteDeCadastro +
                ", categoria=" + categoria +
                ", usuario=" + usuario +
                ", nome='" + nome + '\'' +
                ", valor=" + valor +
                ", quantidade=" + quantidade +
                ", descricao='" + descricao + '\'' +
                ", caracteristicas=" + caracteristicas +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Produto produto = (Produto) o;
        return Objects.equals(id, produto.id) && Objects.equals(instanteDeCadastro, produto.instanteDeCadastro) && Objects.equals(categoria, produto.categoria) && Objects.equals(usuario, produto.usuario) && Objects.equals(nome, produto.nome) && Objects.equals(valor, produto.valor) && Objects.equals(quantidade, produto.quantidade) && Objects.equals(descricao, produto.descricao) && Objects.equals(caracteristicas, produto.caracteristicas);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, instanteDeCadastro, categoria, usuario, nome, valor, quantidade, descricao, caracteristicas);
    }
}
