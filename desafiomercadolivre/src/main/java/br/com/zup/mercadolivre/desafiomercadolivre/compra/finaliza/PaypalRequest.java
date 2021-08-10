package br.com.zup.mercadolivre.desafiomercadolivre.compra.finaliza;

import br.com.zup.mercadolivre.desafiomercadolivre.compra.Compra;
import br.com.zup.mercadolivre.desafiomercadolivre.compra.Gateways;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

public class PaypalRequest implements RetornoPagamentoGateway {

    @Min(0)
    @Max(1)
    private int status;
    @NotBlank
    private String transacaoId;

    public PaypalRequest(int status, String transacaoId) {
        this.status = status;
        this.transacaoId = transacaoId;
    }

    public Transacao toTransacao(Compra compra) {
        return new Transacao(transacaoId, converteStatus(this.status), compra);
    }

    private TransacaoStatus converteStatus(int Status) {
        return this.status == 0 ? TransacaoStatus.ERRO : TransacaoStatus.SUCESSO;
    }

    @Override
    public Gateways getGateway() {
        return Gateways.PAYPAL;
    }

    @Override
    public TransacaoStatus getStatusTransacao() {
        return converteStatus(this.status);
    }
}
