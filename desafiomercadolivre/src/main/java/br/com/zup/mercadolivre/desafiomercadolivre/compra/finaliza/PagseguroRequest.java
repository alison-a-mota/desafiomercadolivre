package br.com.zup.mercadolivre.desafiomercadolivre.compra.finaliza;

import br.com.zup.mercadolivre.desafiomercadolivre.compra.Compra;
import br.com.zup.mercadolivre.desafiomercadolivre.compra.Gateways;

import javax.validation.constraints.NotNull;

public class PagseguroRequest implements RetornoPagamentoGateway {

    @NotNull
    private String transacaoId;
    @NotNull
    private TransacaoStatus status;

    public void setTransacaoId(String transacaoId) {
        this.transacaoId = transacaoId;
    }

    public void setStatus(TransacaoStatus status) {
        this.status = status;
    }

    @Override
    public Transacao toTransacao(Compra compra) {
        return new Transacao(transacaoId, status, compra);
    }

    @Override
    public Gateways getGateway() {
        return Gateways.PAGSEGURO;
    }

    @Override
    public TransacaoStatus getStatusTransacao() {
        return this.status;
    }

    @Override
    public String toString() {
        return "PagseguroForm{" +
                "idTransacao='" + transacaoId + '\'' +
                ", status=" + status +
                '}';
    }
}
