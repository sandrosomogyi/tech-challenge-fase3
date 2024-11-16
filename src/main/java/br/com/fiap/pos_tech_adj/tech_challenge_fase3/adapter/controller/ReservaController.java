package br.com.fiap.pos_tech_adj.tech_challenge_fase3.adapter.controller;

import br.com.fiap.pos_tech_adj.tech_challenge_fase3.domain.Reserva;
import br.com.fiap.pos_tech_adj.tech_challenge_fase3.usecase.CriarReserva;
import br.com.fiap.pos_tech_adj.tech_challenge_fase3.usecase.GerenciarReserva;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservas")
public class ReservaController {

    private final CriarReserva criarReserva;
    private final GerenciarReserva gerenciarReserva;

    @Autowired
    public ReservaController(CriarReserva criarReserva, GerenciarReserva gerenciarReserva) {
        this.criarReserva = criarReserva;
        this.gerenciarReserva = gerenciarReserva;
    }

    @PostMapping
    public ResponseEntity<Reserva> criarReserva(@RequestBody Reserva reserva) {
        Reserva reservaCriada = criarReserva.execute(reserva);
        return new ResponseEntity<>(reservaCriada, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Reserva> atualizar(@PathVariable String id, @RequestBody Reserva reserva) {
        return ResponseEntity.ok(gerenciarReserva.atualizar(id, reserva));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable String id) {
        gerenciarReserva.excluir(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reserva> buscarPorId(@PathVariable String id) {
        return ResponseEntity.ok(gerenciarReserva.buscarPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<Reserva>> buscarTodos() {
        return ResponseEntity.ok(gerenciarReserva.buscarTodos());
    }
}
