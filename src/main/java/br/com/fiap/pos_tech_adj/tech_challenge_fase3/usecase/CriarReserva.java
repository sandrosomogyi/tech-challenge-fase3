package br.com.fiap.pos_tech_adj.tech_challenge_fase3.usecase;

import br.com.fiap.pos_tech_adj.tech_challenge_fase3.domain.entity.Reserva;
import br.com.fiap.pos_tech_adj.tech_challenge_fase3.domain.repository.ReservaRepository;
import br.com.fiap.pos_tech_adj.tech_challenge_fase3.domain.repository.RestauranteRepository;

import java.time.LocalDateTime;
import java.util.List;

public class CriarReserva {

    private final ReservaRepository reservaRepository;
    private final RestauranteRepository restauranteRepository;

    public CriarReserva(ReservaRepository reservaRepository, RestauranteRepository restauranteRepository) {
        this.reservaRepository = reservaRepository;
        this.restauranteRepository = restauranteRepository;
    }

    public Reserva execute(Reserva reserva) {
        // Verificar se o restaurante existe
        var restauranteOpt = restauranteRepository.findById(reserva.getRestauranteId());
        if (restauranteOpt.isEmpty()) {
            throw new IllegalArgumentException("Restaurante não encontrado.");
        }

        var restaurante = restauranteOpt.get();

        // Checar disponibilidade da capacidade no horário desejado
        List<Reserva> reservasExistentes = reservaRepository.findByDataHoraReserva(reserva.getDataHoraReserva());
        int totalReservas = reservasExistentes.stream()
                .filter(r -> r.getRestauranteId().equals(reserva.getRestauranteId()))
                .mapToInt(Reserva::getNumeroDePessoas)
                .sum();

        if (totalReservas + reserva.getNumeroDePessoas() > restaurante.getCapacidade()) {
            throw new IllegalArgumentException("Capacidade excedida para o horário desejado.");
        }

        // Criar a reserva
        return reservaRepository.save(reserva);
    }
}
