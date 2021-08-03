package br.com.zup.mercadolivre.desafiomercadolivre.usuario;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
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

}
