package br.com.zup.mercadolivre.desafiomercadolivre.usuario;

import org.springframework.security.crypto.password.PasswordEncoder;

public class SenhaLimpa {

    private final PasswordEncoder encoder;
    private final String senha;

    public SenhaLimpa(String senha, PasswordEncoder encoder) {
        this.senha = senha;
        this.encoder = encoder;
    }

    public String getHashDaSenha() {
        return encoder.encode(this.senha);
    }
}
