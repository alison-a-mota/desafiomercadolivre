package br.com.zup.mercadolivre.desafiomercadolivre.pergunta;

import br.com.zup.mercadolivre.desafiomercadolivre.produto.Produto;
import br.com.zup.mercadolivre.desafiomercadolivre.usuario.Usuario;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
public class Pergunta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long id;
    @Column(updatable = false)
    private final LocalDateTime instanteCriacao = LocalDateTime.now();

    @NotBlank
    private String titulo;
    @NotNull
    @ManyToOne
    private Usuario usuario;
    @NotNull
    @ManyToOne
    private Produto produto;

    public Pergunta(String titulo, Usuario usuario, Produto produto) {
        this.titulo = titulo;
        this.usuario = usuario;
        this.produto = produto;
    }

    public String getTitulo() {
        return titulo;
    }

    @Deprecated
    public Pergunta() {
    }
}
