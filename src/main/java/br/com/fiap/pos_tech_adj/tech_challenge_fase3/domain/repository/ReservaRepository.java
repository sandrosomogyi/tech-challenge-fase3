package br.com.fiap.pos_tech_adj.tech_challenge_fase3.domain.repository;

import br.com.fiap.pos_tech_adj.tech_challenge_fase3.domain.entity.Reserva;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ReservaRepository {
    Optional<Reserva> findById(String id);
    List<Reserva> findByRestauranteId(String restauranteId);
    List<Reserva> findByClienteId(String clienteId);
    List<Reserva> findByDataHoraReserva(LocalDateTime dataHoraReserva);
    Reserva save(Reserva reserva);
    void deleteById(String id);
}