package br.com.fiap.pos_tech_adj.tech_challenge_fase3.usecase;

import br.com.fiap.pos_tech_adj.tech_challenge_fase3.domain.entity.Reserva;
import br.com.fiap.pos_tech_adj.tech_challenge_fase3.domain.repository.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GerenciarReserva {

    private final ReservaRepository reservaRepository;

    @Autowired
    public GerenciarReserva(ReservaRepository reservaRepository) {
        this.reservaRepository = reservaRepository;
    }

    public Reserva criar(Reserva reserva) {
        return reservaRepository.save(reserva);
    }

    public Reserva atualizar(String id, Reserva reserva) {
        return reservaRepository.findById(id)
                .map(r -> {
                    r.setDataHoraReserva(reserva.getDataHoraReserva());
                    r.setNumeroDePessoas(reserva.getNumeroDePessoas());
                    r.setRestauranteId(reserva.getRestauranteId());
                    return reservaRepository.save(r);
                })
                .orElseThrow(() -> new IllegalArgumentException("Reserva não encontrada."));
    }

    public void excluir(String id) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Reserva não encontrada."));

        reservaRepository.deleteById(id);
    }

    public Reserva buscarPorId(String id) {
        return reservaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Reserva não encontrada."));
    }

    public List<Reserva> buscarTodos() {
        return reservaRepository.findAll();
    }
}
