package br.com.zup.mercadolivre.desafiomercadolivre.compartilhado.notafiscal;

import br.com.zup.mercadolivre.desafiomercadolivre.compra.Compra;
import br.com.zup.mercadolivre.desafiomercadolivre.compra.CompraStatus;
import br.com.zup.mercadolivre.desafiomercadolivre.compra.finaliza.EventoCompraSucesso;
import io.jsonwebtoken.lang.Assert;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile("dev")
@Service
public class NotaFiscalDev implements EventoCompraSucesso {

    @Override
    public void processa(Compra compra) {
        Assert.isTrue(CompraStatus.SUCESSO.equals(compra.getStatus()), "A compra ainda não foi concluída!!");

        System.out.println("Nota fiscal enviada para a compra de ID " +compra.getId()+
                " e comprador com Id " +compra.getUsuarioComprador().getId());
    }
}
