package br.com.fiap.pos_tech_adj.tech_challenge_fase3.adapter.controller.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;
import java.util.stream.Collectors;

@ControllerAdvice
public class ControllerExceptionHandler {

    private StandardError standardError = new StandardError();

    @ExceptionHandler(ControllerNotFoundException.class)
    public ResponseEntity<StandardError> entityNotFound (
            ControllerNotFoundException controllerNotFoundException,
            HttpServletRequest request){

        HttpStatus status = HttpStatus.NOT_FOUND;
        standardError.setTimeStamp(Instant.now());
        standardError.setStatus(status.value());
        standardError.setError("Entity not found");
        standardError.setMessage(controllerNotFoundException.getMessage());
        standardError.setPath(request.getRequestURI());

        return ResponseEntity.status(status).body(this.standardError);
    }

    @ExceptionHandler(ControllerDatabaseException.class)
    public ResponseEntity<StandardError> databaseException (
            ControllerDatabaseException controllerDatabaseException,
            HttpServletRequest request){

        HttpStatus status = HttpStatus.NOT_FOUND;
        standardError.setTimeStamp(Instant.now());
        standardError.setStatus(status.value());
        standardError.setError("Database Exception");
        standardError.setMessage(controllerDatabaseException.getMessage());
        standardError.setPath(request.getRequestURI());

        return ResponseEntity.status(status).body(this.standardError);
    }

    @ExceptionHandler(OptimisticLockingFailureException.class)
    public ResponseEntity<StandardError> optimisticException (
            OptimisticLockingFailureException optimisticLockingFailureException,
            HttpServletRequest request){

        HttpStatus status = HttpStatus.CONFLICT;
        standardError.setTimeStamp(Instant.now());
        standardError.setStatus(status.value());
        standardError.setError("MongoDB Exception: Documento foi atualizado por outro usuário. Tente Novamente");
        standardError.setMessage(optimisticLockingFailureException.getMessage());
        standardError.setPath(request.getRequestURI());

        return ResponseEntity.status(status).body(this.standardError);
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public ResponseEntity<StandardError> duplicateKeyException (
            DuplicateKeyException duplicateKeyException,
            HttpServletRequest request){

        HttpStatus status = HttpStatus.CONFLICT;
        standardError.setTimeStamp(Instant.now());
        standardError.setStatus(status.value());
        standardError.setError("MongoDB Exception: Documento já existe na coleção.");
        standardError.setMessage(duplicateKeyException.getMessage());
        standardError.setPath(request.getRequestURI());

        return ResponseEntity.status(status).body(this.standardError);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardError> methodArgumentNotValidException(
            MethodArgumentNotValidException methodArgumentNotValidException,
            HttpServletRequest request) {

        HttpStatus status = HttpStatus.BAD_REQUEST;
        StandardError standardError = new StandardError();
        standardError.setTimeStamp(Instant.now());
        standardError.setStatus(status.value());
        standardError.setError("Erro de Validação.");

        String errorMessages = methodArgumentNotValidException.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage) // Pega apenas a mensagem padrão de cada erro
                .collect(Collectors.joining("; ")); // Junta as mensagens em uma string separada por ponto e vírgula

        standardError.setMessage(errorMessages);
        standardError.setPath(request.getRequestURI());

        return ResponseEntity.status(status).body(standardError);
    }


    @ExceptionHandler(ControllerMessagingException.class)
    public ResponseEntity<StandardError> messagingException (
            ControllerMessagingException controllerMessagingException,
            HttpServletRequest request){

        HttpStatus status = HttpStatus.CONFLICT;
        standardError.setTimeStamp(Instant.now());
        standardError.setStatus(status.value());
        standardError.setError("Messaging Exception");
        standardError.setMessage(controllerMessagingException.getMessage());
        standardError.setPath(request.getRequestURI());

        return ResponseEntity.status(status).body(this.standardError);
    }
}
