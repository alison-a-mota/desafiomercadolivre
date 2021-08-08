package br.com.zup.mercadolivre.desafiomercadolivre.produto;

import br.com.zup.mercadolivre.desafiomercadolivre.opiniao.OpiniaoDetalhe;
import br.com.zup.mercadolivre.desafiomercadolivre.pergunta.PerguntaDetalhe;
import br.com.zup.mercadolivre.desafiomercadolivre.produto.caracteristica.CaracteristicaDetalhe;
import br.com.zup.mercadolivre.desafiomercadolivre.produto.imagem.Imagem;

import java.math.BigDecimal;
import java.util.Set;

public class ProdutoResponseSite {

    private final String nome;
    private final BigDecimal preco;
    private final String descricao;
    private final Double totalDeNotas;
    private final Double mediaDeNotas;

    private final Set<CaracteristicaDetalhe> caracteristicas;
    private final Set<String> imagens;
    private final Set<PerguntaDetalhe> perguntas;
    private final Set<OpiniaoDetalhe> opinioes;

    public ProdutoResponseSite(Produto produto, Double totalDeNotas, Double media) {
        this.nome = produto.getNome();
        this.preco = produto.getValor();
        this.descricao = produto.getDescricao();
        this.totalDeNotas = totalDeNotas;
        this.mediaDeNotas = media;
        this.caracteristicas = produto.mapCaracteristica(CaracteristicaDetalhe::new);
        this.imagens = produto.mapImagem(Imagem::getLink);
        this.perguntas = produto.mapPerguntas(PerguntaDetalhe::new);
        this.opinioes = produto.mapOpinioes(OpiniaoDetalhe::new);
    }

    public String getNome() {
        return nome;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public String getDescricao() {
        return descricao;
    }

    public Double getTotalDeNotas() {
        return totalDeNotas;
    }

    public Double getMediaDeNotas() {
        return mediaDeNotas;
    }

    public Set<CaracteristicaDetalhe> getCaracteristicas() {
        return caracteristicas;
    }

    public Set<String> getImagens() {
        return imagens;
    }

    public Set<PerguntaDetalhe> getPerguntas() {
        return perguntas;
    }

    public Set<OpiniaoDetalhe> getOpinioes() {
        return opinioes;
    }
}
