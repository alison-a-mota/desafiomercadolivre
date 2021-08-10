package br.com.zup.mercadolivre.desafiomercadolivre.compartilhado.eviaremail;

import br.com.zup.mercadolivre.desafiomercadolivre.compra.Compra;
import br.com.zup.mercadolivre.desafiomercadolivre.compra.finaliza.EventoCompraSucesso;
import com.sendgrid.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Profile("prod")
@Service
public class EnviarEmailServiceProd implements EventoCompraSucesso, EnviarEmailService {

    @Value("${app.sendgrid.key}")
    private String apiKey;
    @Value("${app.sendgrid.templateId}")
    private String templateId;

    private final SendGrid sendGrid;

    public EnviarEmailServiceProd(SendGrid sendGrid) {
        this.sendGrid = sendGrid;
    }

    @Override
    public void enviaEmailGenerico(String destinatarioEmail) {

        Email from = new Email("alisson_jb@yahoo.com.br");
        Email to = new Email(destinatarioEmail);

        String subject = "Você tem uma nova pergunta";

        Content content = new Content("text/plain", "Um cliente fez uma pergunta: ");

        Mail mail = new Mail(from, subject, to, content);

        sendGrid.initializeSendGrid(apiKey);

        Request request = new Request();

        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sendGrid.api(request);

            System.out.println(response.getStatusCode());
            System.out.println(response.getBody());
            System.out.println(response.getHeaders());

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void enviaEmailCompraSucesso(Compra compra) {

        Email from = new Email("alisson_jb@yahoo.com.br");
        Email to = new Email(compra.getUsuarioComprador().getEmail());

        String corpoDoEmail = ("Parabéns " +compra.getUsuarioComprador().getEmail()+ "! Você comprou do" +
                " vendedor " +compra.getUsuarioVendedor().getUsername()+ " " +compra.getQuantidade()+ " "
                +compra.getProduto().getNome()+ " no valor total de R$"
                +compra.getValorDaCompra()+ ".");

        String subject = "Você comprou " +compra.getQuantidade()+ " " +compra.getProduto().getNome();

        Content content = new Content("text/plain", corpoDoEmail);

        Mail mail = new Mail(from, subject, to, content);

        sendGrid.initializeSendGrid(apiKey);

        Request request = new Request();

        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sendGrid.api(request);

            System.out.println(response.getStatusCode());
            System.out.println(response.getBody());
            System.out.println(response.getHeaders());

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void enviaEmailCompraFalhou(String destinatarioEmail, Long compraId) {

        Email from = new Email("alisson_jb@yahoo.com.br");
        Email to = new Email(destinatarioEmail);

        String subject = "O seu pagamento foi negado e precisa ser revisto";

        Content content = new Content("text/plain", "O seu pagamento não foi concluído e precisará ser refeito. " +
                "Você poderá verificar no link http://localhost:8080/api/compra/" +compraId);

        Mail mail = new Mail(from, subject, to, content);

        sendGrid.initializeSendGrid(apiKey);

        Request request = new Request();

        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sendGrid.api(request);

            System.out.println(response.getStatusCode());
            System.out.println(response.getBody());
            System.out.println(response.getHeaders());

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void processa(Compra compra) {
        enviaEmailCompraSucesso(compra);
    }
}