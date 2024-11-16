package br.com.fiap.pos_tech_adj.tech_challenge_fase3.domain.repository;

import br.com.fiap.pos_tech_adj.tech_challenge_fase3.domain.entity.Cliente;

import java.util.List;
import java.util.Optional;

public interface ClienteRepository {
    Cliente save(Cliente cliente);
    List<Cliente> findAll();
    Optional<Cliente> findById(String id);
    Optional<Cliente> findByPessoaId(String pessoaId);
    void deleteById(String id);
}
