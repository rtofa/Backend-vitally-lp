package br.com.vitallyoficial.api.presentation.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardErrorDTO> handleValidationErrors(MethodArgumentNotValidException ex, HttpServletRequest request) {

        List<String> errors = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.toList());

        StandardErrorDTO errorDTO = new StandardErrorDTO(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Erro de Validação",
                request.getRequestURI(),
                errors
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDTO);
    }


    @ExceptionHandler({IllegalArgumentException.class, IllegalStateException.class})
    public ResponseEntity<StandardErrorDTO> handleDomainBusinessErrors(RuntimeException ex, HttpServletRequest request) {

        StandardErrorDTO errorDTO = new StandardErrorDTO(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Violação de Regra de Negócio",
                request.getRequestURI(),
                Collections.singletonList(ex.getMessage())
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDTO);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<StandardErrorDTO> handleGenericErrors(Exception ex, HttpServletRequest request) {

        ex.printStackTrace();

        StandardErrorDTO errorDTO = new StandardErrorDTO(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Erro Interno do Servidor",
                request.getRequestURI(),
                Collections.singletonList("Ocorreu um erro inesperado. Por favor, contacte o suporte técnico.")
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorDTO);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<StandardErrorDTO> handleResourceNotFound(ResourceNotFoundException ex, HttpServletRequest request) {

        StandardErrorDTO errorDTO = new StandardErrorDTO(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                "Recurso Não Encontrado",
                request.getRequestURI(),
                Collections.singletonList(ex.getMessage())
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDTO);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<StandardErrorDTO> handleBadCredentials(BadCredentialsException ex, HttpServletRequest request) {

        StandardErrorDTO errorDTO = new StandardErrorDTO(
                LocalDateTime.now(),
                HttpStatus.UNAUTHORIZED.value(), // Retorna 401 em vez de 500
                "Não Autorizado",
                request.getRequestURI(),
                Collections.singletonList("E-mail ou senha incorretos.") // Mensagem amigável para o Frontend
        );

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorDTO);
    }

}