package br.com.fiap.pos_tech_adj.tech_challenge_fase3.domain.repository;

import br.com.fiap.pos_tech_adj.tech_challenge_fase3.domain.entity.Pessoa;
import java.util.Optional;
import java.util.List;

public interface PessoaRepository {
    Pessoa save(Pessoa pessoa);
    Optional<Pessoa> findById(String id);
    Optional<Pessoa> findByEmail(String email);
    Optional<Pessoa> findByCpf(String cpf);
    List<Pessoa> findAll();
    void deleteById(String id);
}
