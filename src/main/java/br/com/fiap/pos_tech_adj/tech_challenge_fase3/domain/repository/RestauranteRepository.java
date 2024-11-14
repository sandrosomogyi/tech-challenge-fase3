package br.com.fiap.pos_tech_adj.tech_challenge_fase3.domain.repository;

import br.com.fiap.pos_tech_adj.tech_challenge_fase3.domain.entity.Restaurante;

import java.util.Optional;
import java.util.List;

public interface RestauranteRepository {
    Optional<Restaurante> findById(String id);
    List<Restaurante> findAll();
    List<Restaurante> findByLocalizacao(String localizacao);
    List<Restaurante> findByTipoDeCozinha(String tipoDeCozinha);
    Restaurante save(Restaurante restaurante);
    void deleteById(String id);
}