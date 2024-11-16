package br.com.fiap.pos_tech_adj.tech_challenge_fase3.usecase;

import br.com.fiap.pos_tech_adj.tech_challenge_fase3.adapter.controller.exception.ControllerMessagingException;
import br.com.fiap.pos_tech_adj.tech_challenge_fase3.adapter.controller.exception.ControllerNotFoundException;
import br.com.fiap.pos_tech_adj.tech_challenge_fase3.domain.entity.Reserva;
import br.com.fiap.pos_tech_adj.tech_challenge_fase3.domain.repository.ClienteRepository;
import br.com.fiap.pos_tech_adj.tech_challenge_fase3.domain.repository.ReservaRepository;
import br.com.fiap.pos_tech_adj.tech_challenge_fase3.domain.repository.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
        var restauranteOpt = restauranteRepository.findById(reserva.getRestaurante().getId());
        if (restauranteOpt.isEmpty()) {
            throw new ControllerNotFoundException("Restaurante não encontrado.");
        }

        reserva.setRestaurante(restauranteOpt.get());

        // Verificar se o cliente existe
        var clienteOpt = clienteRepository.findById(reserva.getCliente().getId());
        if (clienteOpt.isEmpty()) {
            throw new ControllerNotFoundException("Cliente não encontrado.");
        }

        reserva.setCliente(clienteOpt.get());

        // Checar disponibilidade da capacidade
        List<Reserva> reservasExistentes = buscarReservasDoDia(reserva.getDataHoraReserva().toLocalDate());
        int totalReservas = reservasExistentes.stream()
                .filter(r -> r.getRestaurante().equals(reserva.getRestaurante()))
                .mapToInt(Reserva::getNumeroDePessoas)
                .sum();

        if (totalReservas + reserva.getNumeroDePessoas() > reserva.getRestaurante().getCapacidade()) {
            throw new ControllerMessagingException("Capacidade excedida para o dia desejado.");
        }

        // Criar a reserva
        var reservaSaved = reservaRepository.save(reserva);

        clienteOpt.get().getReservas().add(reservaSaved);
        clienteRepository.save(clienteOpt.get());

        return reservaSaved;
    }

    public List<Reserva> buscarReservasDoDia(LocalDate data) {
        // Define o início e o fim do dia
        LocalDateTime inicioDoDia = data.atStartOfDay(); // 00:00:00
        LocalDateTime fimDoDia = data.atTime(LocalTime.MAX); // 23:59:59.999999999

        // Realiza a consulta com base no intervalo
        return reservaRepository.findByDataHoraReservaBetween(inicioDoDia, fimDoDia);
    }
}
