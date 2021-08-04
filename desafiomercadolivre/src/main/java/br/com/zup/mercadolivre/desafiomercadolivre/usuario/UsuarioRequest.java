package br.com.zup.mercadolivre.desafiomercadolivre.usuario;

import br.com.zup.mercadolivre.desafiomercadolivre.compartilhado.anotacoes.CampoUnico;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class UsuarioRequest {

    @NotBlank
    @Email
    @CampoUnico(fieldName = "email", domainClass = Usuario.class)
    private String email;
    @NotBlank
    @Size(min = 6)
    private String senha;

    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }

    public Usuario toModel() {
        return new Usuario(this.email, new UsuarioSenhaLimpa(this.senha));
    }
}
