package br.com.fiap.pos_tech_adj.tech_challenge_fase3.adapter.repository;

import br.com.fiap.pos_tech_adj.tech_challenge_fase3.domain.entity.Restaurante;
import br.com.fiap.pos_tech_adj.tech_challenge_fase3.domain.repository.RestauranteRepository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MongoRestauranteRepository extends MongoRepository<Restaurante, String>, RestauranteRepository {

    List<Restaurante> findByLocalizacao(String localizacao);
    List<Restaurante> findByTipoCozinha(String tipoCozinha);

}