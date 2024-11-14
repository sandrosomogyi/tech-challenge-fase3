package br.com.fiap.pos_tech_adj.tech_challenge_fase3.domain.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "avaliacoes")
public class Avaliacao {

    @Id
    private String id;
    private String restauranteId;
    private String clienteId;
    private int nota;
    private String comentario;
}
