package br.com.zup.mercadolivre.desafiomercadolivre.categoria;

import br.com.zup.mercadolivre.desafiomercadolivre.compartilhado.anotacoes.CampoUnico;
import br.com.zup.mercadolivre.desafiomercadolivre.compartilhado.anotacoes.ExistsById;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.constraints.NotBlank;

public class CategoriaRequest {

    @NotBlank
    @CampoUnico(fieldName = "nome", domainClass = Categoria.class)
    private String nome;
    @ExistsById(fieldName = "id", domainClass = Categoria.class)
    private Long categoriaMae;

    public String getNome() {
        return nome;
    }

    public Long getCategoriaMae() {
        return categoriaMae;
    }

    public Categoria toModel(CategoriaRepository categoriaRepository) {

        if (this.categoriaMae != null) {
            Categoria categoriaMae = categoriaRepository.findById(this.categoriaMae).orElseThrow(() -> new ResponseStatusException(HttpStatus.
                    NOT_FOUND, "Esta categoriaMae não foi encontrada"));
            return new Categoria(this.nome, categoriaMae);
        }
        return new Categoria(this.nome);
    }
}