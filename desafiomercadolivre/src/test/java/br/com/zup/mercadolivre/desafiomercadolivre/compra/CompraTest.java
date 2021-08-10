package br.com.zup.mercadolivre.desafiomercadolivre.compra;

import br.com.zup.mercadolivre.desafiomercadolivre.categoria.Categoria;
import br.com.zup.mercadolivre.desafiomercadolivre.compra.finaliza.PaypalRequest;
import br.com.zup.mercadolivre.desafiomercadolivre.produto.Produto;
import br.com.zup.mercadolivre.desafiomercadolivre.produto.caracteristica.CaracteristicaRequest;
import br.com.zup.mercadolivre.desafiomercadolivre.usuario.Usuario;
import br.com.zup.mercadolivre.desafiomercadolivre.usuario.UsuarioSenhaLimpa;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static br.com.zup.mercadolivre.desafiomercadolivre.compra.CompraStatus.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.util.ReflectionTestUtils.setField;

class CompraTest {


    @Test
    @DisplayName("Não deve alterar o status da compra caso a compra tenha status Sucesso")
    void naoDeveAlterarStatusCasoStatusCompraSejaSucesso() {
        var usuario = new Usuario("email@email.com", new UsuarioSenhaLimpa("senha"));
        var caracteristicas = new CaracteristicaRequest("nome", "descrição");
        var compra = new Compra(new Produto(new Categoria("marcio"),
                usuario, "nome", BigDecimal.valueOf(10),
                1L, "descrição", List.of(caracteristicas, caracteristicas, caracteristicas)),
                1, usuario, Gateways.PAYPAL);

        setField(compra, "status", SUCESSO);

        var paypalRequest = new PaypalRequest(1, "1");

        compra.verificaESetaStatus(paypalRequest);

        assertEquals(SUCESSO, compra.getStatus());
    }

    @Test
    @DisplayName("Deve alterar o status da compra para FALHA caso status da compra seja INICIADA e status da transação seja ERRO")
    void deveAlterarParaFalhaCasoStatusSejaIniciadaETransacaoSejaErro() {
        var usuario = new Usuario("email@email.com", new UsuarioSenhaLimpa("senha"));
        var caracteristicas = new CaracteristicaRequest("nome", "descrição");
        var compra = new Compra(new Produto(new Categoria("marcio"),
                usuario, "nome", BigDecimal.valueOf(10),
                1L, "descrição", List.of(caracteristicas, caracteristicas, caracteristicas)),
                1, usuario, Gateways.PAYPAL);

        setField(compra, "status", INICIADA);

        var paypalRequest = new PaypalRequest(0, "1");

        compra.verificaESetaStatus(paypalRequest);

        assertEquals(FALHA, compra.getStatus());
    }

    @Test
    @DisplayName("Deve alterar o status da compra para FALHA caso status da compra seja FALHA e status da transação seja ERRO")
    void deveAlterarParaFalhaCasoStatusSejaFalhaETransacaoSejaErro() {
        var usuario = new Usuario("email@email.com", new UsuarioSenhaLimpa("senha"));
        var caracteristicas = new CaracteristicaRequest("nome", "descrição");
        var compra = new Compra(new Produto(new Categoria("marcio"),
                usuario, "nome", BigDecimal.valueOf(10),
                1L, "descrição", List.of(caracteristicas, caracteristicas, caracteristicas)),
                1, usuario, Gateways.PAYPAL);

        setField(compra, "status", FALHA);

        var paypalRequest = new PaypalRequest(0, "1");

        compra.verificaESetaStatus(paypalRequest);

        assertEquals(FALHA, compra.getStatus());
    }

    @Test
    @DisplayName("Deve alterar Status para SUCESSO caso status da compra seja INICIADA e status da transação seja SUCESSO")
    void deveAlterarParaSucessoCasoStatusSejaIniciadaETransacaoSejaSucesso() {
        var usuario = new Usuario("email@email.com", new UsuarioSenhaLimpa("senha"));
        var caracteristicas = new CaracteristicaRequest("nome", "descrição");
        var compra = new Compra(new Produto(new Categoria("marcio"),
                usuario, "nome", BigDecimal.valueOf(10),
                1L, "descrição", List.of(caracteristicas, caracteristicas, caracteristicas)),
                1, usuario, Gateways.PAYPAL);

        //
        setField(compra, "status", INICIADA);

        var paypalRequest = new PaypalRequest(1, "1");

        compra.verificaESetaStatus(paypalRequest);

        assertEquals(SUCESSO, compra.getStatus());
    }

    @Test
    @DisplayName("Deve alterar Status para SUCESSO caso status da compra seja FALHA e status da transação seja SUCESSO")
    void deveAlterarParaSucessoCasoStatusSejaFalhaETransacaoSejaSucesso() {
        var usuario = new Usuario("email@email.com", new UsuarioSenhaLimpa("senha"));
        var caracteristicas = new CaracteristicaRequest("nome", "descrição");
        var compra = new Compra(new Produto(new Categoria("marcio"),
                usuario, "nome", BigDecimal.valueOf(10),
                1L, "descrição", List.of(caracteristicas, caracteristicas, caracteristicas)),
                1, usuario, Gateways.PAYPAL);

        setField(compra, "status", FALHA);

        var paypalRequest = new PaypalRequest(1, "1");

        compra.verificaESetaStatus(paypalRequest);

        assertEquals(SUCESSO, compra.getStatus());
    }
}