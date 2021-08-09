package br.com.zup.mercadolivre.desafiomercadolivre.compra;

import br.com.zup.mercadolivre.desafiomercadolivre.produto.Produto;
import br.com.zup.mercadolivre.desafiomercadolivre.usuario.Usuario;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class CompraRequest {

    @NotNull
    private Long produtoId;
    @NotNull
    @Positive
    private int quantidade;
    @NotNull
    private CompraGateway gateway;

    public Long getProdutoId() {
        return produtoId;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public CompraGateway getGateway() {
        return gateway;
    }

    public Compra toModel(Usuario usuarioLogado, Produto produto) {
        return new Compra(produto, quantidade, usuarioLogado, gateway);
    }
}
