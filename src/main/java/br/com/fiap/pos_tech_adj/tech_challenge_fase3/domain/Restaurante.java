package br.com.fiap.pos_tech_adj.tech_challenge_fase3.domain;

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

    // Construtor para inicializar o id, caso precise em seus testes
    public Restaurante(String id, String nome, String localizacao, String tipoCozinha, int capacidade) {
        this.id = id;
        this.nome = nome;
        this.localizacao = localizacao;
        this.tipoCozinha = tipoCozinha;
        this.capacidade = capacidade;
    }
}
