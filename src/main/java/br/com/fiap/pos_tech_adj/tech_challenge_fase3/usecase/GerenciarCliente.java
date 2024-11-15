package br.com.fiap.pos_tech_adj.tech_challenge_fase3.usecase;

import br.com.fiap.pos_tech_adj.tech_challenge_fase3.domain.entity.Cliente;
import br.com.fiap.pos_tech_adj.tech_challenge_fase3.domain.entity.Pessoa;
import br.com.fiap.pos_tech_adj.tech_challenge_fase3.domain.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GerenciarCliente {

    private final ClienteRepository clienteRepository;

    @Autowired
    public GerenciarCliente(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public Cliente criar(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    public Cliente atualizar(String id, Cliente cliente) {
        return clienteRepository.findById(id)
                .map(existingClient -> {
                    existingClient.setPessoa(cliente.getPessoa());
                    existingClient.setReservas(cliente.getReservas());
                    return clienteRepository.save(existingClient);
                })
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado."));
    }

    public void excluir(String id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado."));

        clienteRepository.deleteById(id);
    }

    public Cliente buscarPorId(String id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado."));
    }

    public List<Cliente> buscarTodos() {
        return clienteRepository.findAll();
    }
}
