package br.com.fiap.pos_tech_adj.tech_challenge_fase3.usecase;

import br.com.fiap.pos_tech_adj.tech_challenge_fase3.domain.entity.Reserva;
import br.com.fiap.pos_tech_adj.tech_challenge_fase3.domain.repository.ClienteRepository;
import br.com.fiap.pos_tech_adj.tech_challenge_fase3.domain.repository.ReservaRepository;
import br.com.fiap.pos_tech_adj.tech_challenge_fase3.domain.repository.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CriarReserva {

    private final ReservaRepository reservaRepository;
    private final RestauranteRepository restauranteRepository;
    private final ClienteRepository clienteRepository;

    @Autowired
    public CriarReserva(
            ReservaRepository reservaRepository,
            RestauranteRepository restauranteRepository,
            ClienteRepository clienteRepository) {
        this.reservaRepository = reservaRepository;
        this.restauranteRepository = restauranteRepository;
        this.clienteRepository = clienteRepository;
    }

    public Reserva execute(Reserva reserva) {
        // Verificar se o restaurante existe
        var restauranteOpt = restauranteRepository.findById(reserva.getRestauranteId().getId());
        if (restauranteOpt.isEmpty()) {
            throw new IllegalArgumentException("Restaurante não encontrado.");
        }

        var restaurante = restauranteOpt.get();

        // Verificar se o cliente existe
        var clienteOpt = clienteRepository.findById(reserva.getClienteId().getId());
        if (clienteOpt.isEmpty()) {
            throw new IllegalArgumentException("Cliente não encontrado.");
        }

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
