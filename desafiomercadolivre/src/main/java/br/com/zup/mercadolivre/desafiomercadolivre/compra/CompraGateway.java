package br.com.zup.mercadolivre.desafiomercadolivre.compra;

public enum CompraGateway {

    PAYPAL {
        @Override
        public String UrlRedirecionamento(Long compraId, String gatewayReturn) {
            return "/paypal.com?buyerId=" +compraId+ "&redirectUrl=" +gatewayReturn;
        }
    },

    PAGSEGURO {

        @Override
        public String UrlRedirecionamento(Long compraId, String gatewayReturn) {
            //A URL do gateway será implementada futuramente
            return "/pagseguro.com?returnId=" +compraId+ "&redirectUrl=" +gatewayReturn;
        }
    };

    public abstract String UrlRedirecionamento(Long compraId, String gatewayReturn);
}
