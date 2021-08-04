package br.com.zup.mercadolivre.desafiomercadolivre.compartilhado.security;

public class TokenResponse {
    private final String token;
    private final String tipo;

    public TokenResponse(String token, String tipo) {
        this.token = token;
        this.tipo = tipo;
    }

    public String getToken() {
        return token;
    }

    public String getTipo() {
        return tipo;
    }
}
