package br.com.zup.mercadolivre.desafiomercadolivre.compra.finaliza;

import br.com.zup.mercadolivre.desafiomercadolivre.compra.Compra;
import br.com.zup.mercadolivre.desafiomercadolivre.compra.Gateways;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Transacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long id;
    @Column(updatable = false)
    private final LocalDateTime instanteProcessamento = LocalDateTime.now();

    @NotNull
    private String transacaoGatewayId;
    @NotNull
    @Enumerated(value = EnumType.STRING)
    @Column(updatable = false)
    private TransacaoStatus status;
    @NotNull
    @ManyToOne
    private Compra compra;

    public Transacao(String transacaoGatewayId, TransacaoStatus status, Compra compra) {
        this.transacaoGatewayId = transacaoGatewayId;
        this.status = status;
        this.compra = compra;
    }

    @Deprecated
    public Transacao() {
    }

    public boolean concluidaComSucesso(){
        return this.status.equals(TransacaoStatus.SUCESSO);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transacao transacao = (Transacao) o;
        return Objects.equals(transacaoGatewayId, transacao.transacaoGatewayId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(transacaoGatewayId, status);
    }
}
