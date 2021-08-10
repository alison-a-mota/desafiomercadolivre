package br.com.zup.mercadolivre.desafiomercadolivre.compartilhado.eviaremail;

import br.com.zup.mercadolivre.desafiomercadolivre.compra.Compra;
import br.com.zup.mercadolivre.desafiomercadolivre.compra.finaliza.EventoCompraSucesso;
import com.sendgrid.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;

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

    public void enviaEmailGenerico(String destinatarioEmail) {

        System.out.println("Email destinatário >>>>>>>>>>>>>> "+destinatarioEmail);

        Email from = new Email("alisson_jb@yahoo.com.br");
        Email to = new Email(destinatarioEmail);

        String subject = "Você tem uma nova pergunta!";

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

    public void enviaEmailCompraSucesso(String destinatarioEmail) {

        Email from = new Email("alisson_jb@yahoo.com.br");
        Email to = new Email(destinatarioEmail);

        String subject = "A sua compra foi concluída!";

        Content content = new Content("text/plain", "O seu pagamento foi aprovado e a compra foi concluída!");

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

    public void enviaEmailCompraFalhou(String destinatarioEmail) {

        Email from = new Email("alisson_jb@yahoo.com.br");
        Email to = new Email(destinatarioEmail);

        String subject = "O seu pagamento foi negado e precisa ser revisto";

        Content content = new Content("text/plain", "O seu pagamento não foi concluído e precisará ser refeito");

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
        System.out.println("Enviou o e-mail");
    }
}