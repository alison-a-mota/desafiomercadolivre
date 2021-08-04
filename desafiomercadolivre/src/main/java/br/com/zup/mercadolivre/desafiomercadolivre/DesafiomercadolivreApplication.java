package br.com.zup.mercadolivre.desafiomercadolivre;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class DesafiomercadolivreApplication {

    public static void main(String[] args) {
        SpringApplication.run(DesafiomercadolivreApplication.class, args);
    }

}
