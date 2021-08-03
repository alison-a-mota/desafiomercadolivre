package br.com.zup.mercadolivre.desafiomercadolivre.compartilhado.anotacoes;

import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CampoUnicoValidator implements ConstraintValidator<CampoUnico, Object> {

    private String atributo;
    private Class<?> classe;

    @Autowired
    private EntityManager entityManager;

    @Override
    public void initialize(CampoUnico campoUnico) {
        atributo = campoUnico.fieldName();
        classe = campoUnico.domainClass();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        var resultList = entityManager.createQuery("SELECT 1 FROM " + classe.getName() + " WHERE " + atributo + " = ?1").setParameter(1, value)
                .getResultList();
        return resultList.isEmpty();
    }
}