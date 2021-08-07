package br.com.zup.mercadolivre.desafiomercadolivre.compartilhado.eviaremail;

import com.sendgrid.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class EnviarEmailService {

    @Value("${app.sendgrid.key}")
    private String apiKey;
    @Value("${app.sendgrid.templateId}")
    private String templateId;

    private final SendGrid sendGrid;

    public EnviarEmailService(SendGrid sendGrid) {
        this.sendGrid = sendGrid;
    }

    public String enviaEmail(String destinatarioEmail, String pergunta) {

        System.out.println("Email destinatário >>>>>>>>>>>>>> "+destinatarioEmail);

        Email from = new Email("alisonalvesmota@gmail.com");
        Email to = new Email(destinatarioEmail);

        String subject = "Você tem uma nova pergunta!";

        Content content = new Content("text/plain", "Um cliente fez uma pergunta: "+pergunta);

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
            return "Erro ao tentar enviar e-mail";
        }
        return "E-mail enviado";
    }

}
