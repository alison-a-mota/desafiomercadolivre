package br.com.zup.mercadolivre.desafiomercadolivre.compra;

import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

public enum Gateways {

    PAYPAL {
        @Override
        public URI UrlRedirecionamento(Long compraId, String gatewayReturn) {
            String url = "www.paypal.com?buyerId=" + compraId + "&redirectUrl=" + gatewayReturn;
            return UriComponentsBuilder.newInstance().scheme("http").host(url).build().toUri();
        }
    },

    PAGSEGURO {
        @Override
        public URI UrlRedirecionamento(Long compraId, String gatewayReturn) {
            String url = "www.pagseguro.com?returnId=" + compraId + "&redirectUrl=" + gatewayReturn;
            return UriComponentsBuilder.newInstance().scheme("http").host(url).build().toUri();
        }
    };

    public abstract URI UrlRedirecionamento(Long compraId, String gatewayReturn);

}