package br.com.fiap.pos_tech_adj.tech_challenge_fase3.usecase;

import br.com.fiap.pos_tech_adj.tech_challenge_fase3.adapter.controller.exception.ControllerNotFoundException;
import br.com.fiap.pos_tech_adj.tech_challenge_fase3.domain.Reserva;
import br.com.fiap.pos_tech_adj.tech_challenge_fase3.domain.repository.ClienteRepository;
import br.com.fiap.pos_tech_adj.tech_challenge_fase3.domain.repository.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GerenciarReserva {

    private final ReservaRepository reservaRepository;
    private final ClienteRepository clienteRepository;

    @Autowired
    public GerenciarReserva(ReservaRepository reservaRepository, ClienteRepository clienteRepository) {
        this.reservaRepository = reservaRepository;
        this.clienteRepository = clienteRepository;
    }

    public Reserva criar(Reserva reserva) {
        return reservaRepository.save(reserva);
    }

    public Reserva atualizar(String id, Reserva reserva) {

        var reservaSaved = reservaRepository.findById(id)
                .map(r -> {
                    r.setDataHoraReserva(reserva.getDataHoraReserva());
                    r.setNumeroDePessoas(reserva.getNumeroDePessoas());
                    r.setRestaurante(reserva.getRestaurante());
                    return reservaRepository.save(r);
                })
                .orElseThrow(() -> new ControllerNotFoundException("Reserva não encontrada."));

        var cliente = clienteRepository.findById(reserva.getCliente().getId());
        cliente.get().getReservas().removeIf(x -> x.getCliente().getId().equals(reserva.getCliente().getId()));
        cliente.get().getReservas().add(reservaSaved);
        clienteRepository.save(cliente.get());

        return reservaSaved;
    }

    public void excluir(String id) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new ControllerNotFoundException("Reserva não encontrada."));

        var cliente = clienteRepository.findById(reserva.getCliente().getId());
        cliente.get().getReservas().removeIf(x -> x.getCliente().getId().equals(reserva.getCliente().getId()));
        clienteRepository.save(cliente.get());

        reservaRepository.deleteById(id);
    }

    public Reserva buscarPorId(String id) {
        return reservaRepository.findById(id)
                .orElseThrow(() -> new ControllerNotFoundException("Reserva não encontrada."));
    }

    public List<Reserva> buscarTodos() {
        return reservaRepository.findAll();
    }
}
