package br.com.zup.mercadolivre.desafiomercadolivre.produto;

import br.com.zup.mercadolivre.desafiomercadolivre.categoria.Categoria;
import br.com.zup.mercadolivre.desafiomercadolivre.categoria.CategoriaRepository;
import br.com.zup.mercadolivre.desafiomercadolivre.compartilhado.anotacoes.ExistsById;
import br.com.zup.mercadolivre.desafiomercadolivre.produto.caracteristica.CaracteristicaRequest;
import br.com.zup.mercadolivre.desafiomercadolivre.usuario.Usuario;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ProdutoRequest {

    @NotBlank
    private String nome;
    @NotNull
    @Positive
    private BigDecimal valor;
    @NotNull
    @PositiveOrZero
    private Long quantidade;
    @NotBlank
    @Size(max = 1000)
    private String descricao;
    @Size(min = 3)
    @Valid
    private final List<CaracteristicaRequest> caracteristicas = new ArrayList<>();

    @NotNull
    @ExistsById(fieldName = "id", domainClass = Categoria.class)
    private Long categoriaId;
    @NotNull
    @ExistsById(fieldName = "id", domainClass = Usuario.class)
    private Long usuarioId;


    public Produto toModel(CategoriaRepository categoriaRepository, Usuario usuario) {
        Categoria categoria = categoriaRepository.findById(this.getCategoriaId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoria não encontrada"));
        return new Produto(categoria, usuario, this.nome, this.valor, this.quantidade, this.descricao, caracteristicas);
    }


    public String getNome() {
        return nome;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public Long getQuantidade() {
        return quantidade;
    }

    public String getDescricao() {
        return descricao;
    }

    public Long getCategoriaId() {
        return categoriaId;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public List<CaracteristicaRequest> getCaracteristicas() {
        return caracteristicas;
    }
}
