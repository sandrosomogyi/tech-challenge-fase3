package br.com.fiap.pos_tech_adj.tech_challenge_fase3.usecase;

import br.com.fiap.pos_tech_adj.tech_challenge_fase3.adapter.controller.exception.ControllerNotFoundException;
import br.com.fiap.pos_tech_adj.tech_challenge_fase3.domain.Pessoa;
import br.com.fiap.pos_tech_adj.tech_challenge_fase3.domain.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GerenciarPessoa {

    private final PessoaRepository pessoaRepository;

    @Autowired
    public GerenciarPessoa(PessoaRepository pessoaRepository) {
        this.pessoaRepository = pessoaRepository;
    }

    public Pessoa criar(Pessoa pessoa) {
        return pessoaRepository.save(pessoa);
    }

    public Pessoa atualizar(String id, Pessoa pessoa) {
        return pessoaRepository.findById(id)
                .map(p -> {
                    p.setEmail(pessoa.getEmail());
                    p.setNome(pessoa.getNome());
                    p.setSobrenome(pessoa.getSobrenome());
                    p.setCpf(pessoa.getCpf());
                    p.setTelefone(pessoa.getTelefone());
                    p.setSenha(pessoa.getSenha());
                    return pessoaRepository.save(p);
                })
                .orElseThrow(() -> new ControllerNotFoundException("Pessoa não encontrada."));
    }

    public void excluir(String id) {
        Pessoa pessoa = pessoaRepository.findById(id)
                .orElseThrow(() -> new ControllerNotFoundException("Pessoa não encontrada."));

        pessoaRepository.deleteById(id);
    }

    public Pessoa buscarPorId(String id) {
        return pessoaRepository.findById(id)
                .orElseThrow(() -> new ControllerNotFoundException("Pessoa não encontrada."));
    }

    public List<Pessoa> buscarTodos() {
        return pessoaRepository.findAll();
    }
}
