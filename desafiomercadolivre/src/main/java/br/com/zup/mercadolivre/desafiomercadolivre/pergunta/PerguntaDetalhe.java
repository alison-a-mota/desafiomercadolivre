package br.com.zup.mercadolivre.desafiomercadolivre.pergunta;

public class PerguntaDetalhe {

    private final String titulo;

    public PerguntaDetalhe(Pergunta pergunta) {
        this.titulo = pergunta.getTitulo();
    }

    public String getTitulo() {
        return titulo;
    }
}
