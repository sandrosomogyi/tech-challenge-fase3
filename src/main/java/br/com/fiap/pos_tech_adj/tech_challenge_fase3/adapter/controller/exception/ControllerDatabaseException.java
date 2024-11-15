package br.com.fiap.pos_tech_adj.tech_challenge_fase3.adapter.controller.exception;

public class ControllerDatabaseException extends RuntimeException {

    public ControllerDatabaseException(String message) {
        super(message);
    }

    public ControllerDatabaseException(String message, Throwable cause) {
        super(message, cause);
    }
}