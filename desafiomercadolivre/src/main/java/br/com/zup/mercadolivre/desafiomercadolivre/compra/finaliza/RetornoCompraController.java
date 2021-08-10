package br.com.zup.mercadolivre.desafiomercadolivre.compra.finaliza;

import br.com.zup.mercadolivre.desafiomercadolivre.compra.Compra;
import br.com.zup.mercadolivre.desafiomercadolivre.compra.CompraRepository;
import br.com.zup.mercadolivre.desafiomercadolivre.compra.CompraStatus;
import io.jsonwebtoken.lang.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Set;

/**
 * - Precisa validar o gateway da compra; CHECK
 * - Setar status da compra para SUCESSO; CHECK
 * - Setar status da compra para FALHA; CHECK
 * - Tratar rertorno dos Asserts do Alberto; CHECK
 * - Após o status da compra ser alterado pra Salvo, não permitir alterar; CHECK
 * - Corrigir retorno para o Gateway com o endpoint da nossa API; CHECK
 * - Corrigir o retorno ao salvar a compra (está retornando String); CHECK
 * - Criar as classes de envio de e-mail, NF e Ranking Vendedores;
 * - Fazer o perfil Prod x Dev;
 *
 * @Profile("Dev")
 */


@RestController
@RequestMapping
public class RetornoCompraController {

    private final CompraRepository compraRepository;
    private final Set<EventoCompraSucesso> eventoCompraSucessoSet;

    public RetornoCompraController(CompraRepository compraRepository, Set<EventoCompraSucesso> eventoCompraSucessoSet) {
        this.compraRepository = compraRepository;
        this.eventoCompraSucessoSet = eventoCompraSucessoSet;
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

    private String processa(Long compraId, RetornoPagamentoGateway retornoPagamentoGateway) {


        Compra compra = compraRepository.findById(compraId).get();
        Assert.isTrue(compra.getGateway().equals(retornoPagamentoGateway.getGateway()),
                "Esse pagamento é de outro Gateway");
        Assert.isTrue(CompraStatus.SUCESSO.equals(compra.getStatus()),
                "Esa compra já foi encerrada");

        compra.adicionaTransacao(retornoPagamentoGateway);

        compra.verificaESetaStatus(retornoPagamentoGateway);
        compraRepository.save(compra);

        //Padrão Observer => estudar
        if(CompraStatus.SUCESSO.equals(compra.getStatus())){
            eventoCompraSucessoSet.forEach(evento -> evento.processa(compra));
            System.out.println("Chamou os métodos");
        }

        return "foi";
    }
}