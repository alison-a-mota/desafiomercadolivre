package br.com.zup.mercadolivre.desafiomercadolivre.compartilhado.eviaremail;

import br.com.zup.mercadolivre.desafiomercadolivre.compra.Compra;

public interface EnviarEmailService {

    void enviaEmailGenerico(String email);

    void enviaEmailCompraSucesso(Compra compra);

    void enviaEmailCompraFalhou(String destinatarioEmail, Long compraId);
}
