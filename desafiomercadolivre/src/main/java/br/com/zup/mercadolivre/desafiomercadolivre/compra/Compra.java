package br.com.zup.mercadolivre.desafiomercadolivre.compra;

import br.com.zup.mercadolivre.desafiomercadolivre.compra.finaliza.RetornoPagamentoGateway;
import br.com.zup.mercadolivre.desafiomercadolivre.compra.finaliza.Transacao;
import br.com.zup.mercadolivre.desafiomercadolivre.compra.finaliza.TransacaoStatus;
import br.com.zup.mercadolivre.desafiomercadolivre.produto.Produto;
import br.com.zup.mercadolivre.desafiomercadolivre.usuario.Usuario;
import io.jsonwebtoken.lang.Assert;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

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
    private Gateways gateway;
    @OneToMany(mappedBy = "compra", cascade = CascadeType.MERGE)
    private Set<Transacao> transacoes = new HashSet<>();

    /**
     * O construtor da compra está definindo: o valor do produto na hora da compra,
     * o valor total da compra e atualizando o estoque do produto.
     */
    public Compra(@NotNull Produto produto, @NotNull int quantidade, @NotNull Usuario usuarioComprador, @NotNull
            Gateways gateway) {
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

    public String getUrlRedirecionamento() {
        return gateway.UrlRedirecionamento(this.getId(), gatewayReturn());
    }

    /**
     * Não sei como será implementado esse método.
     */
    private String gatewayReturn() {
        return "gateway_return";
    }

    public void adicionaTransacao(RetornoPagamentoGateway request) {
        Transacao novaTransacao = request.toTransacao(this);
        Assert.isTrue(!this.transacoes.contains(novaTransacao), "Já existe uma transação igual a essa salva");

        Set<Transacao> transacoesConcluidasComSucesso =
                this.transacoes.stream().filter(Transacao::concluidaComSucesso).collect(Collectors.toSet());
        Assert.isTrue(transacoesConcluidasComSucesso.isEmpty(), "Essa compra já foi concluída");

        this.transacoes.add(novaTransacao);
    }

    private Set<Transacao> transacoesComSucesso() {
        Set<Transacao> transacoesComSucesso = this.transacoes.stream().filter(Transacao::concluidaComSucesso).collect(Collectors.toSet());
        Assert.isTrue(transacoesComSucesso.size() <= 1,
                "Vish deu ruim. Tem mais de uma compra com transação Com Sucesso! compraId: " + this.id);

        return transacoesComSucesso;
    }

    public boolean processadaComSucesso() {
        return !transacoesComSucesso().isEmpty();
    }

    public String verificaESetaStatus(RetornoPagamentoGateway retornoPagamentoGateway) {
        if (status.equals(CompraStatus.SUCESSO)) {
            return "Esta compra já foi concluída, o Status não pode ser alterado";
        }
        if (!status.equals(CompraStatus.SUCESSO) && retornoPagamentoGateway.getStatusTransacao().equals(TransacaoStatus.ERRO)){
            setStatus(CompraStatus.FALHA);
            return "O Gateway retornou Erro de pagamento, o Status da compra foi alterado para Falha.";
        }
        if (!status.equals(CompraStatus.SUCESSO) && retornoPagamentoGateway.getStatusTransacao().equals(TransacaoStatus.SUCESSO)){
            setStatus(CompraStatus.SUCESSO);
            return "O Gateway retornou Erro de pagamento, o Status da compra foi alterado para Falha.";
        } return "Houve algum erro não previsto";
    }

    public Long getId() {
        return id;
    }

    public Gateways getGateway() {
        return gateway;
    }

    public void setStatus(CompraStatus status) {
//        Assert.isTrue(this.status.equals(CompraStatus.SUCESSO), "Esta compra já foi concluída e o status não pode ser alterado.");
        this.status = status;
    }

    @Deprecated
    public Compra() {
    }

    @Override
    public String toString() {
        return "Compra{" +
                "id=" + id +
                ", produto=" + produto +
                ", quantidade=" + quantidade +
                ", usuarioComprador=" + usuarioComprador +
                ", gateway=" + gateway +
                ", transacoes=" + transacoes +
                '}';
    }
}
