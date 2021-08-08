package br.com.zup.mercadolivre.desafiomercadolivre.opiniao;

public class OpiniaoDetalhe {

    private final String titulo;
    private final int nota;
    private final String descricao;

    public OpiniaoDetalhe(Opiniao opiniao) {
        this.titulo = opiniao.getTitulo();
        this.nota = opiniao.getNota();
        this.descricao = opiniao.getDescricao();
    }

    public String getTitulo() {
        return titulo;
    }

    public int getNota() {
        return nota;
    }

    public String getDescricao() {
        return descricao;
    }
}
