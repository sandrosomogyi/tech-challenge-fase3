package br.com.fiap.pos_tech_adj.tech_challenge_fase3.domain.repository;

import br.com.fiap.pos_tech_adj.tech_challenge_fase3.domain.entity.Administrador;

import java.util.List;
import java.util.Optional;

public interface AdministradorRepository {
    Administrador save(Administrador administrador);
    List<Administrador> findAll();
    Optional<Administrador> findById(String id);
    Optional<Administrador> findByPessoaId(String pessoaId);
    void deleteById(String id);
}
