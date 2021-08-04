package br.com.zup.mercadolivre.desafiomercadolivre.usuario;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class SenhaLimpa {

    private final String senha;

    public SenhaLimpa(String senha) {
        this.senha = senha;
    }

    public String getHashDaSenha() {
        return new BCryptPasswordEncoder().encode(this.senha);
    }
}
