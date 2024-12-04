package br.com.fiap.pos_tech_adj.tech_challenge_fase3.usecase;

import br.com.fiap.pos_tech_adj.tech_challenge_fase3.adapter.controller.exception.ControllerNotFoundException;
import br.com.fiap.pos_tech_adj.tech_challenge_fase3.domain.entity.Reserva;
import br.com.fiap.pos_tech_adj.tech_challenge_fase3.domain.repository.ClienteRepository;
import br.com.fiap.pos_tech_adj.tech_challenge_fase3.domain.repository.ReservaRepository;
import br.com.fiap.pos_tech_adj.tech_challenge_fase3.domain.repository.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CancelarReserva {

    private final ReservaRepository reservaRepository;
    private final RestauranteRepository restauranteRepository;
    private final ClienteRepository clienteRepository;

    @Autowired
    public CancelarReserva(
            ReservaRepository reservaRepository,
            RestauranteRepository restauranteRepository,
            ClienteRepository clienteRepository) {
        this.reservaRepository = reservaRepository;
        this.restauranteRepository = restauranteRepository;
        this.clienteRepository = clienteRepository;
    }

    public void execute(Reserva reserva) {
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

        // Remover a reserva
        reservaRepository.deleteById(reserva.getId());

        clienteOpt.get().getReservas().removeIf(x -> x.getId().equals(reserva.getId()));
        clienteRepository.save(clienteOpt.get());
    }
}
