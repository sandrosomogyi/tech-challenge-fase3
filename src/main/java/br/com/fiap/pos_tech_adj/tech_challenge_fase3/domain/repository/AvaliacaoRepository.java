package br.com.fiap.pos_tech_adj.tech_challenge_fase3.domain.repository;

import br.com.fiap.pos_tech_adj.tech_challenge_fase3.domain.entity.Avaliacao;

import java.util.List;
import java.util.Optional;

public interface AvaliacaoRepository {
    List<Avaliacao> findAll();
    Optional<Avaliacao> findById(String id);
    List<Avaliacao> findByRestauranteId(String restauranteId);
    List<Avaliacao> findByClienteId(String clienteId);
    Avaliacao save(Avaliacao avaliacao);
    void deleteById(String id);
}