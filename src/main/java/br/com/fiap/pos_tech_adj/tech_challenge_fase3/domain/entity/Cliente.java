package br.com.fiap.pos_tech_adj.tech_challenge_fase3.domain.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Cliente {

    @Id
    private String id;

    @DBRef
    private List<Reserva> reservas = new ArrayList<>(); // Inicializa a lista vazia

    @DBRef
    private List<Avaliacao> avaliacoes = new ArrayList<>(); // Inicializa a lista vazia

    @DBRef
    @Indexed(unique = true)  // Definindo o campo `pessoa` como único na base de dados
    private Pessoa pessoa;

    @Version
    private Long version;
}
