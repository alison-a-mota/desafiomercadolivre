package br.com.zup.mercadolivre.desafiomercadolivre.compartilhado.anotacoes;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;


/**
 * Valida se existe um objeto do tipo String salvo no banco de dados.
 *
 * @author Alison Alves
 * @version 1.1.0
 *
 * @see Character#isWhitespace(char)
 */

@Documented

//Definir esse tipo de annotation como uma constraint de bean validation
@Constraint(validatedBy = {CampoUnicoValidator.class})

//Onde nossas annotations podem ser usadas.
@Target(FIELD)

//Especifica como a annotation marcada é armazenada. Escolhemos RUNTIME, para que possa ser usado pelo ambiente de tempo de execução.
@Retention(RUNTIME)

public @interface CampoUnico {

    //Mensagem caso não valide
    String message() default "Já existe um objeto igual salvo no banco.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    //Campo que será validado
    String fieldName();

    //Classe que será aiksdfjaosdfh asd
    Class<?> domainClass();
}
