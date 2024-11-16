package br.com.fiap.pos_tech_adj.tech_challenge_fase3.adapter.repository;

import br.com.fiap.pos_tech_adj.tech_challenge_fase3.domain.Administrador;
import br.com.fiap.pos_tech_adj.tech_challenge_fase3.domain.repository.AdministradorRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MongoAdministradorRepository extends MongoRepository<Administrador, String>, AdministradorRepository {

    @Override
    Optional<Administrador> findByPessoaId(String pessoaId);

}
