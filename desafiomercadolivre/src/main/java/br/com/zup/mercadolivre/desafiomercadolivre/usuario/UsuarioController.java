package br.com.zup.mercadolivre.desafiomercadolivre.usuario;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder encoder;

    public UsuarioController(UsuarioRepository usuarioRepository, PasswordEncoder encoder) {
        this.usuarioRepository = usuarioRepository;
        this.encoder = encoder;
    }

    @PostMapping
    public ResponseEntity<?> cria(@Valid @RequestBody UsuarioRequest usuarioRequest) {

        Usuario usuario = usuarioRequest.toModel(encoder);
        usuarioRepository.save(usuario);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
