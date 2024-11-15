package br.com.fiap.pos_tech_adj.tech_challenge_fase3.usecase;

import br.com.fiap.pos_tech_adj.tech_challenge_fase3.domain.entity.Cliente;
import br.com.fiap.pos_tech_adj.tech_challenge_fase3.domain.entity.Pessoa;
import br.com.fiap.pos_tech_adj.tech_challenge_fase3.domain.repository.ClienteRepository;
import br.com.fiap.pos_tech_adj.tech_challenge_fase3.domain.repository.PessoaRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CadastrarCliente {

    private final ClienteRepository clienteRepository;
    private final PessoaRepository pessoaRepository;

    public CadastrarCliente(ClienteRepository clienteRepository, PessoaRepository pessoaRepository) {
        this.clienteRepository = clienteRepository;
        this.pessoaRepository = pessoaRepository;
    }

    public Cliente execute(String pessoaId) {
        Optional<Pessoa> pessoaOptional = pessoaRepository.findById(pessoaId);
        if (pessoaOptional.isEmpty()) {
            throw new IllegalArgumentException("Pessoa não encontrada.");
        }

        Pessoa pessoa = pessoaOptional.get();

        if (clienteRepository.findByPessoaId(pessoaId).isPresent()) {
            throw new IllegalArgumentException("Já existe um cliente associado a essa pessoa.");
        }

        Cliente cliente = new Cliente();
        cliente.setPessoa(pessoa);

        return clienteRepository.save(cliente);
    }
}
