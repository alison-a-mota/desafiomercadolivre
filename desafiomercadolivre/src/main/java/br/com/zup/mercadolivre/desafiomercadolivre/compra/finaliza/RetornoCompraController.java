package br.com.zup.mercadolivre.desafiomercadolivre.compra.finaliza;

import br.com.zup.mercadolivre.desafiomercadolivre.compra.Compra;
import br.com.zup.mercadolivre.desafiomercadolivre.compra.CompraRepository;
import br.com.zup.mercadolivre.desafiomercadolivre.compra.CompraStatus;
import io.jsonwebtoken.lang.Assert;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class RetornoCompraController {

    private final CompraRepository compraRepository;
    private final EventosDeCompraFinalizadaService eventosDeCompraFinalizada;

    public RetornoCompraController(CompraRepository compraRepository, EventosDeCompraFinalizadaService eventosDeCompraFinalizada) {
        this.compraRepository = compraRepository;
        this.eventosDeCompraFinalizada = eventosDeCompraFinalizada;
    }

    @PostMapping("/retorno-pagseguro/{compraId}")
    public ResponseEntity<TransacaoStatus> respostaPagseguro(@Valid PagseguroRequest pagseguroRequest,
                                    @PathVariable Long compraId) {
        processa(compraId, pagseguroRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(pagseguroRequest.getStatusTransacao());
    }

    @PostMapping("/retorno-paypal/{compraId}")
    public ResponseEntity<TransacaoStatus> respostaPaypal(@Valid PaypalRequest paypalRequest,
                                         @PathVariable Long compraId) {
        processa(compraId, paypalRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(paypalRequest.getStatusTransacao());
    }

    private void processa(Long compraId, RetornoPagamentoGateway retornoPagamentoGateway) {

        Compra compra = compraRepository.findById(compraId).orElseThrow(()->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Compra não encontrada"));
        Assert.isTrue(compra.getGateway().equals(retornoPagamentoGateway.getGateway()),
                "Esse pagamento é de outro Gateway");
        Assert.isTrue(!CompraStatus.SUCESSO.equals(compra.getStatus()),
                "Essa compra já foi encerrada");

        compra.adicionaTransacao(retornoPagamentoGateway);

        compra.verificaESetaStatus(retornoPagamentoGateway);
        compraRepository.save(compra);
        eventosDeCompraFinalizada.processa(compra);
    }
}