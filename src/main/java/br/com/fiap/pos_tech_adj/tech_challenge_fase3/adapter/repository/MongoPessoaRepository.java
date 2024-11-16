package br.com.fiap.pos_tech_adj.tech_challenge_fase3.adapter.repository;

import br.com.fiap.pos_tech_adj.tech_challenge_fase3.domain.Pessoa;
import br.com.fiap.pos_tech_adj.tech_challenge_fase3.domain.repository.PessoaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MongoPessoaRepository extends MongoRepository<Pessoa, String>, PessoaRepository {

    @Override
    Optional<Pessoa> findByEmail(String email);

    @Override
    Optional<Pessoa> findByCpf(String cpf);
}
