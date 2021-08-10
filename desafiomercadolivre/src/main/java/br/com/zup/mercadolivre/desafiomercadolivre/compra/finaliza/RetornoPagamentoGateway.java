package br.com.zup.mercadolivre.desafiomercadolivre.compra.finaliza;

import br.com.zup.mercadolivre.desafiomercadolivre.compra.Compra;
import br.com.zup.mercadolivre.desafiomercadolivre.compra.Gateways;

public interface RetornoPagamentoGateway {

    /**
     * Instancia uma Transação conforme o Gateway escolhido
     */
    public Transacao toTransacao(Compra compra);
    public Gateways getGateway();
    public TransacaoStatus getStatusTransacao();
}
