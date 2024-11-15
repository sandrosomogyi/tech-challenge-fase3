package br.com.fiap.pos_tech_adj.tech_challenge_fase3.adapter.controller.exception;

import lombok.Data;

import java.time.Instant;

@Data
public class StandardError {
    private Instant timeStamp;
    private Integer status;
    private String error;
    private String message;
    private String path;
}
