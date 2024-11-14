package br.com.fiap.pos_tech_adj.tech_challenge_fase3.adapter.controller;

import br.com.fiap.pos_tech_adj.tech_challenge_fase3.domain.entity.Reserva;
import br.com.fiap.pos_tech_adj.tech_challenge_fase3.usecase.CriarReserva;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservas")
public class ReservaController {

    private final CriarReserva criarReserva;

    @Autowired
    public ReservaController(CriarReserva criarReserva) {
        this.criarReserva = criarReserva;
    }

    @PostMapping
    public ResponseEntity<Reserva> criarReserva(@RequestBody Reserva reserva) {
        Reserva reservaCriada = criarReserva.execute(reserva);
        return new ResponseEntity<>(reservaCriada, HttpStatus.CREATED);
    }
}
