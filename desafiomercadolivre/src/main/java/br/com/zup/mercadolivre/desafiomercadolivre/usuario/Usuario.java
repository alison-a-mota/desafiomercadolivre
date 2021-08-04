package br.com.zup.mercadolivre.desafiomercadolivre.usuario;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDateTime;

@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long id;
    @Column(updatable = false)
    private final LocalDateTime dataCadastro = LocalDateTime.now();

    @NotBlank
    @Email
    @Column(unique = true)
    private String login;
    @NotBlank
    @Size(min = 6)
    private String senha;

    /**
     * @param usuarioSenhaLimpa Precisa ser uma senha descriptografada pois o construtor da classe modelo irá criptografar.
     */
    public Usuario(@Email @NotBlank String login, @NotNull @Size(min = 6) UsuarioSenhaLimpa usuarioSenhaLimpa) {
        this.login = login;
        this.senha = usuarioSenhaLimpa.getHashDaSenha();
    }

    @Deprecated
    public Usuario() {
    }
}