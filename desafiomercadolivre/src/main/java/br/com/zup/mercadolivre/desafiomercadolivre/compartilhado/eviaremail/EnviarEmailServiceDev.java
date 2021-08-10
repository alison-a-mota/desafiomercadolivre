package br.com.zup.mercadolivre.desafiomercadolivre.compartilhado.eviaremail;

import br.com.zup.mercadolivre.desafiomercadolivre.compra.Compra;
import br.com.zup.mercadolivre.desafiomercadolivre.compra.finaliza.EventoCompraSucesso;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile("dev")
@Service
public class EnviarEmailServiceDev implements EventoCompraSucesso, EnviarEmailService {

    @Override
    public void enviaEmailGenerico(String destinatarioEmail) {
        System.out.println("Enviado email genérico para o destinatário em ambiente dev >>>>>>>>>>>>>> " + destinatarioEmail);
    }

    public void enviaEmailCompraSucesso(Compra compra) {
        System.out.println(("Parabéns " +compra.getUsuarioComprador().getEmail()+ "! Você comprou do" +
                " vendedor " +compra.getUsuarioVendedor().getUsername()+ " " +compra.getQuantidade()+ " "
                +compra.getProduto().getNome()+ " no valor total de R$"
                +compra.getValorDaCompra()+ "."));
    }

    public void enviaEmailCompraFalhou(String destinatarioEmail, Long compraId) {
        System.out.println("Enviado o seguinte e-mail: O seu pagamento não foi concluído e precisará ser refeito. " +
                "Você poderá verificar no link http://localhost:8080/api/compra/" +compraId);
    }

    @Override
    public void processa(Compra compra) {
        enviaEmailCompraSucesso(compra);
    }
}