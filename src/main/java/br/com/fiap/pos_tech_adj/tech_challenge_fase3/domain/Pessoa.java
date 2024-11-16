package br.com.fiap.pos_tech_adj.tech_challenge_fase3.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class Pessoa {

    @Id
    private String id;

    @Indexed(unique = true)  // Definindo o campo `email` como único na base de dados
    private String email;
    private String nome;
    private String sobrenome;

    @Indexed(unique = true)  // Definindo o campo `cpf` como único na base de dados
    private String cpf;

    private String telefone;
    private String senha;

    @Version
    private Long version;
}