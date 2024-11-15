package br.com.fiap.pos_tech_adj.tech_challenge_fase3.usecase;

import br.com.fiap.pos_tech_adj.tech_challenge_fase3.domain.entity.Pessoa;
import br.com.fiap.pos_tech_adj.tech_challenge_fase3.domain.repository.PessoaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CadastrarPessoa {

    private final PessoaRepository pessoaRepository;

    public CadastrarPessoa(PessoaRepository pessoaRepository) {
        this.pessoaRepository = pessoaRepository;
    }

    public Pessoa execute(Pessoa pessoa) {
        if (pessoa.getNome() == null || pessoa.getNome().isEmpty()) {
            throw new IllegalArgumentException("O nome é obrigatório.");
        }
        if (pessoa.getEmail() == null || pessoa.getEmail().isEmpty()) {
            throw new IllegalArgumentException("O email é obrigatório.");
        }
        if (pessoa.getCpf() == null || pessoa.getCpf().isEmpty()) {
            throw new IllegalArgumentException("O CPF é obrigatório.");
        }

        if (pessoaRepository.findByEmail(pessoa.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Já existe uma pessoa com este email.");
        }

        if (pessoaRepository.findByCpf(pessoa.getCpf()).isPresent()) {
            throw new IllegalArgumentException("Já existe uma pessoa com este CPF.");
        }

        return pessoaRepository.save(pessoa);
    }
}
