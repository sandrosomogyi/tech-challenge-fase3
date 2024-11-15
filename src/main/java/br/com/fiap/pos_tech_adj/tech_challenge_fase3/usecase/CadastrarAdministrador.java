package br.com.fiap.pos_tech_adj.tech_challenge_fase3.usecase;

import br.com.fiap.pos_tech_adj.tech_challenge_fase3.domain.entity.Administrador;
import br.com.fiap.pos_tech_adj.tech_challenge_fase3.domain.entity.Pessoa;
import br.com.fiap.pos_tech_adj.tech_challenge_fase3.domain.repository.AdministradorRepository;
import br.com.fiap.pos_tech_adj.tech_challenge_fase3.domain.repository.PessoaRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CadastrarAdministrador {

    private final AdministradorRepository administradorRepository;
    private final PessoaRepository pessoaRepository;

    public CadastrarAdministrador(AdministradorRepository administradorRepository, PessoaRepository pessoaRepository) {
        this.administradorRepository = administradorRepository;
        this.pessoaRepository = pessoaRepository;
    }

    public Administrador execute(String pessoaId) {
        Optional<Pessoa> pessoaOptional = pessoaRepository.findById(pessoaId);
        if (pessoaOptional.isEmpty()) {
            throw new IllegalArgumentException("Pessoa não encontrada.");
        }

        Pessoa pessoa = pessoaOptional.get();

        if (administradorRepository.findByPessoaId(pessoaId).isPresent()) {
            throw new IllegalArgumentException("Já existe um administrador associado a essa pessoa.");
        }

        Administrador administrador = new Administrador();
        administrador.setPessoa(pessoa);

        return administradorRepository.save(administrador);
    }
}
