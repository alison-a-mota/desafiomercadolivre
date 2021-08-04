package br.com.zup.mercadolivre.desafiomercadolivre.compartilhado.handlers;

public class ExceptionResponse {

    private final String campo;
    private final String mensagem;
    private final String status;

    public ExceptionResponse(String campo, String mensagem, String status) {
        this.campo = campo;
        this.mensagem = mensagem;
        this.status = status;
    }

    public String getCampo() {
        return campo;
    }

    public String getMensagem() {
        return mensagem;
    }

    public String getStatus() {
        return status;
    }
}
