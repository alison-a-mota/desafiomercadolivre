package br.com.zup.mercadolivre.desafiomercadolivre.compartilhado.notafiscal;

import br.com.zup.mercadolivre.desafiomercadolivre.compra.Compra;
import br.com.zup.mercadolivre.desafiomercadolivre.compra.CompraStatus;
import br.com.zup.mercadolivre.desafiomercadolivre.compra.finaliza.EventoCompraSucesso;
import io.jsonwebtoken.lang.Assert;
import org.springframework.stereotype.Service;

@Service
public class NotaFiscal implements EventoCompraSucesso {

    @Override
    public void processa(Compra compra) {
        Assert.isTrue(CompraStatus.SUCESSO.equals(compra.getStatus()), "A compra ainda não foi concluída!!");

        System.out.println("Nota fiscal enviada para a compra " +compra.getId()+
                " do comprador com Id " +compra.getUsuarioComprador().getId());
    }
}
