package br.com.zup.mercadolivre.desafiomercadolivre.compartilhado.rankingvendedores;

import br.com.zup.mercadolivre.desafiomercadolivre.compra.Compra;
import br.com.zup.mercadolivre.desafiomercadolivre.compra.CompraStatus;
import br.com.zup.mercadolivre.desafiomercadolivre.compra.finaliza.EventoCompraSucesso;
import io.jsonwebtoken.lang.Assert;
import org.springframework.stereotype.Service;

@Service
public class RankingVendedores implements EventoCompraSucesso {
    @Override
    public void processa(Compra compra) {
        Assert.isTrue(CompraStatus.SUCESSO.equals(compra.getStatus()), "A compra ainda não foi concluída!!");
    }
}
