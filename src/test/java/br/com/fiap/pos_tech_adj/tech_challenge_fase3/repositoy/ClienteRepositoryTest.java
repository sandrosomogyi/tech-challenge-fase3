package br.com.fiap.pos_tech_adj.tech_challenge_fase3.repositoy;

import br.com.fiap.pos_tech_adj.tech_challenge_fase3.domain.entity.Cliente;
import br.com.fiap.pos_tech_adj.tech_challenge_fase3.domain.entity.Pessoa;
import br.com.fiap.pos_tech_adj.tech_challenge_fase3.domain.repository.ClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class ClienteRepositoryTest {

    @Mock
    private ClienteRepository clienteRepository;

    private Cliente cliente;
    private Pessoa pessoa;

    @BeforeEach
    void setUp() {
        pessoa = new Pessoa();
        pessoa.setEmail("bruno@gmail.com");
        pessoa.setNome("bruno");
        pessoa.setSobrenome("User");
        pessoa.setCpf("12345678909");
        pessoa.setTelefone("999999999");
        pessoa.setSenha("password");

        cliente = new Cliente();
        cliente.setId("cliente123");
        cliente.setPessoa(pessoa);
    }

    @Test
    void deveSalvarCliente() {
        when(clienteRepository.save(cliente)).thenReturn(cliente);

        Cliente savedCliente = clienteRepository.save(cliente);
        assertNotNull(savedCliente);
        assertEquals(cliente.getId(), savedCliente.getId());
        verify(clienteRepository, times(1)).save(cliente);
    }

    @Test
    void deveBuscarClientePorId() {
        when(clienteRepository.findById("cliente123")).thenReturn(Optional.of(cliente));

        Optional<Cliente> foundCliente = clienteRepository.findById("cliente123");
        assertTrue(foundCliente.isPresent());
        assertEquals(cliente.getId(), foundCliente.get().getId());
        verify(clienteRepository, times(1)).findById("cliente123");
    }

    @Test
    void deveBuscarClientePorPessoaId() {
        when(clienteRepository.findByPessoaId("12345678909")).thenReturn(Optional.of(cliente));

        Optional<Cliente> foundCliente = clienteRepository.findByPessoaId("12345678909");
        assertTrue(foundCliente.isPresent());
        assertEquals(cliente.getPessoa().getCpf(), foundCliente.get().getPessoa().getCpf());
        verify(clienteRepository, times(1)).findByPessoaId("12345678909");
    }

    @Test
    void deveListarTodosClientes() {
        when(clienteRepository.findAll()).thenReturn(List.of(cliente));
        List<Cliente> allClientes = clienteRepository.findAll();
        assertFalse(allClientes.isEmpty());
        assertEquals(1, allClientes.size());
        verify(clienteRepository, times(1)).findAll();
    }

    @Test
    void deveDeletarCliente() {
        doNothing().when(clienteRepository).deleteById(cliente.getId());
        clienteRepository.deleteById(cliente.getId());
        verify(clienteRepository, times(1)).deleteById(cliente.getId());
    }
}
