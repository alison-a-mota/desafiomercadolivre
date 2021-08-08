package br.com.zup.mercadolivre.desafiomercadolivre.produto.caracteristica;

import br.com.zup.mercadolivre.desafiomercadolivre.produto.Produto;

import javax.validation.constraints.NotBlank;

public class CaracteristicaRequest {

    @NotBlank
    private final String nome;
    @NotBlank
    private final String descricao;

    public CaracteristicaRequest(@NotBlank String nome, @NotBlank String descricao) {
        this.nome = nome;
        this.descricao = descricao;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public Caracteristica toModel(Produto produto) {
        return new Caracteristica(nome, descricao, produto);
    }
}
