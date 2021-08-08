package br.com.zup.mercadolivre.desafiomercadolivre.produto.caracteristica;

public class CaracteristicaDetalhe {

    private final String nome;
    private final String descricao;

    public CaracteristicaDetalhe(Caracteristica caracteristica) {
        this.nome = caracteristica.getNome();
        this.descricao = caracteristica.getDescricao();
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }
}
