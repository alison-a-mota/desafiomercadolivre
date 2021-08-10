package br.com.zup.mercadolivre.desafiomercadolivre.compartilhado.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ExceptionHandlers {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> interceptaBeanValidation(MethodArgumentNotValidException exception) {
        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
        String campos = fieldErrors.stream().map(FieldError::getField).collect(Collectors.joining(", "));
        String mensagens = fieldErrors.stream().map(FieldError::getDefaultMessage).collect(Collectors.joining(", "));

        ExceptionResponse response = new ExceptionResponse(campos, mensagens, String.valueOf(HttpStatus.BAD_REQUEST));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> interceptaBeanValidation(HttpMessageNotReadableException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Corpo da requisição inválido.");
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> interceptaBeanValidation(IllegalArgumentException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }
}