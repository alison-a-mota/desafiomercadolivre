package br.com.zup.mercadolivre.desafiomercadolivre.compra.finaliza;

public enum TransacaoStatus {
    SUCESSO,
    ERRO;

    public TransacaoStatus corrigeStatus() {
        if(this.equals(TransacaoStatus.SUCESSO)){
            return TransacaoStatus.SUCESSO;
        } return TransacaoStatus.ERRO;
    }
}
