package br.com.zup.mercadolivre.desafiomercadolivre.produto.imagem;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UploaderFake {

    /**
     * @param imagens
     * @return Links para as imagens que foram salvas no servidor. Esta classe é para fazer uma simulação
     */

    public Set<String> envia(List<MultipartFile> imagens) {
        return imagens.stream()
                .map(imagem -> "https://bucket.io/"
                        + imagem.getOriginalFilename())
                .collect(Collectors.toSet());
    }
}
