package br.com.zup.mercadolivre.desafiomercadolivre.usuario;

import br.com.zup.mercadolivre.desafiomercadolivre.compartilhado.anotacoes.CampoUnico;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class UsuarioRequest {

    @NotBlank
    @Email
    @CampoUnico(fieldName = "login", domainClass = Usuario.class)
    private String login;
    @NotBlank
    @Size(min = 6)
    private String senha;

    public String getLogin() {
        return login;
    }

    public String getSenha() {
        return senha;
    }

    public Usuario toModel(PasswordEncoder encoder) {
        return new Usuario(this.login, new SenhaLimpa(this.senha, encoder));
    }
}
