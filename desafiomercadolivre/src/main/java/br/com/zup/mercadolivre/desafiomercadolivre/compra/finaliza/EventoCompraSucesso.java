package br.com.zup.mercadolivre.desafiomercadolivre.compra.finaliza;

import br.com.zup.mercadolivre.desafiomercadolivre.compra.Compra;

/**
 * Todo evento relacionado ao sucesso de uma nova compra deve implementar essa interface
 */

public interface EventoCompraSucesso {
    void processa(Compra compra);
}
