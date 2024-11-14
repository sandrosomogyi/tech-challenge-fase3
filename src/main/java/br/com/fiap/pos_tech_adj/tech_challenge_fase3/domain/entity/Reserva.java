package br.com.fiap.pos_tech_adj.tech_challenge_fase3.domain.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "reservas")
public class Reserva {

    @Id
    private String id;
    private String restauranteId;
    private String clienteId;
    private LocalDateTime dataHoraReserva;
    private int numeroDePessoas;
}
