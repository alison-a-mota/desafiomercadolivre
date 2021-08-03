package br.com.zup.mercadolivre.desafiomercadolivre.compartilhado.anotacoes;

import org.hibernate.validator.constraints.CompositionType;
import org.hibernate.validator.constraints.ConstraintComposition;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Valida se é um CPF ou CNPJ válido.
 *
 * @author Alison Alves
 * @version 1.1.0
 */

@Documented

//Definir esse tipo de annotation como uma constraint de bean validation
@Constraint(validatedBy = { })

//Onde nossas annotations podem ser usadas.
@Target({FIELD, PARAMETER})

//Especifica como a annotation marcada é armazenada. Escolhemos RUNTIME, para que possa ser usado pelo ambiente de tempo de execução.
@Retention(RUNTIME)

@CPF
@CNPJ
@ConstraintComposition(CompositionType.OR)

public @interface CpfOuCnpj {

    Class<? extends Payload>[] payload() default { };

    Class<?>[] groups() default { };

    String message() default "Não é um CPF ou um CNPJ válido.";
}
