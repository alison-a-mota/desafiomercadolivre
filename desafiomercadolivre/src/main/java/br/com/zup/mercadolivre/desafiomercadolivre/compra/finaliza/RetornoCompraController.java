package br.com.zup.mercadolivre.desafiomercadolivre.compra.finaliza;

import br.com.zup.mercadolivre.desafiomercadolivre.compra.Compra;
import br.com.zup.mercadolivre.desafiomercadolivre.compra.CompraRepository;
import br.com.zup.mercadolivre.desafiomercadolivre.compra.CompraStatus;
import io.jsonwebtoken.lang.Assert;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * - Precisa validar o gateway da compra; CHECK
 * - Setar status da compra após salvar;
 * - Após o status da compra ser alterado pra Salvo, não permitir alterar;
 * - Criar as classes de envio de e-mail, NF, etc;
 * - Trataertorno dos Asserts do Alberto
 */


@RestController
@RequestMapping
public class RetornoCompraController {

    private final CompraRepository compraRepository;

    public RetornoCompraController(CompraRepository compraRepository) {
        this.compraRepository = compraRepository;
    }

    @PostMapping("/api/retorno-pagseguro/{compraId}")
    public String respostaPagseguro(@Valid PagseguroRequest pagseguroRequest,
                                @PathVariable Long compraId) {

        return processa(compraId, pagseguroRequest);
    }

    @PostMapping("/api/retorno-paypal/{compraId}")
    public String respostaPaypal(@Valid PaypalRequest paypalRequest,
                                  @PathVariable Long compraId) {

        return processa(compraId, paypalRequest);
    }

    private String processa(Long compraId, RetornoPagamentoGateway retornoPagamentoGateway){

        Compra compra = compraRepository.findById(compraId).get();
        Assert.isTrue(compra.getGateway().equals(retornoPagamentoGateway.getGateway()), "Esse pagamento é de outro Gateway");
        compra.adicionaTransacao(retornoPagamentoGateway);

        String retorno = compra.verificaESetaStatus(retornoPagamentoGateway);
        compraRepository.save(compra);


        return retorno;
//        return "foi";
    }
}
