package br.com.fiap.pos_tech_adj.tech_challenge_fase3.domain.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@Document(collection = "restaurantes")
public class Restaurante {

    @Id
    private String id;
    private String nome;
    private String localizacao;
    private String tipoCozinha;
    private int capacidade;
    private List<Avaliacao> avaliacoes = new ArrayList<>(); // Inicializa a lista vazia
}
