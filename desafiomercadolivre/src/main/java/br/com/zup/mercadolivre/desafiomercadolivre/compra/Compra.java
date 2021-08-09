package br.com.zup.mercadolivre.desafiomercadolivre.compra;

import br.com.zup.mercadolivre.desafiomercadolivre.produto.Produto;
import br.com.zup.mercadolivre.desafiomercadolivre.usuario.Usuario;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class Compra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long id;
    private final LocalDateTime instanteDaCompra = LocalDateTime.now();
    @Enumerated(value = EnumType.STRING)
    private CompraStatus status = CompraStatus.INICIADA;

    @NotNull
    @ManyToOne
    private Produto produto;
    @NotNull
    @Positive
    private int quantidade;
    @NotNull
    @ManyToOne
    private Usuario usuarioComprador;

    @NotNull
    private BigDecimal valorProdutoNaHoraDaCompra;
    @NotNull
    private BigDecimal valorDaCompra;
    @NotNull
    @Enumerated(value = EnumType.STRING)
    private CompraGateway gateway;

    /**
     *  O construtor da compra está definindo: o valor do produto na hora da compra,
     *  o valor total da compra e atualizando o estoque do produto.
     */
    public Compra(@NotNull Produto produto, @NotNull int quantidade, @NotNull Usuario usuarioComprador, @NotNull
            CompraGateway gateway) {
        this.produto = produto;
        this.quantidade = quantidade;
        this.usuarioComprador = usuarioComprador;
        this.valorProdutoNaHoraDaCompra = produto.getValor();
        this.valorDaCompra = calculaCompra(produto, quantidade);
        this.gateway = gateway;

        produto.atualizaEstoque(quantidade);
    }

    private BigDecimal calculaCompra(Produto produto, int quantidade) {
        return produto.getValor().multiply(BigDecimal.valueOf(quantidade));
    }

    public Long getId() {
        return id;
    }

    public String getUrlRedirecionamento() {
        return gateway.UrlRedirecionamento(this.getId(), gatewayReturn());
    }

    /**
     * Não sei como será implementado esse método.
     */
    private String gatewayReturn() {
        return "gateway_return";
    }

    @Deprecated
    public Compra() {
    }
}
