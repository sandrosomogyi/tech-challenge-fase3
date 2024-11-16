package br.com.fiap.pos_tech_adj.tech_challenge_fase3.adapter.repository;

import br.com.fiap.pos_tech_adj.tech_challenge_fase3.domain.Avaliacao;
import br.com.fiap.pos_tech_adj.tech_challenge_fase3.domain.repository.AvaliacaoRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MongoAvaliacaoRepository extends MongoRepository<Avaliacao, String>, AvaliacaoRepository {

    List<Avaliacao> findByRestauranteId(String restauranteId);
    List<Avaliacao> findByClienteId(String clienteId);

}
