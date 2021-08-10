package br.com.zup.mercadolivre.desafiomercadolivre.compra.finaliza;

import br.com.zup.mercadolivre.desafiomercadolivre.compartilhado.eviaremail.EnviarEmailService;
import br.com.zup.mercadolivre.desafiomercadolivre.compra.Compra;
import br.com.zup.mercadolivre.desafiomercadolivre.compra.CompraStatus;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class EventosDeCompraFinalizadaService {

    private final EnviarEmailService enviarEmailService;
    private final Set<EventoCompraSucesso> eventoCompraSucessoSet;

    public EventosDeCompraFinalizadaService(EnviarEmailService enviarEmailService, Set<EventoCompraSucesso> eventoCompraSucessoSet) {
        this.enviarEmailService = enviarEmailService;
        this.eventoCompraSucessoSet = eventoCompraSucessoSet;
    }

    public void processa(Compra compra) {
        if (CompraStatus.SUCESSO.equals(compra.getStatus())) {
            eventoCompraSucessoSet.forEach(evento -> evento.processa(compra));
        } else {
            enviarEmailService.enviaEmailCompraFalhou(compra.getUsuarioComprador().getEmail(), compra.getId());
        }
    }
}

