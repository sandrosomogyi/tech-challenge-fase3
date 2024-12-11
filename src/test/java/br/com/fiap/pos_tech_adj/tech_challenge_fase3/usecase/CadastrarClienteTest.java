package br.com.fiap.pos_tech_adj.tech_challenge_fase3.usecase;

import br.com.fiap.pos_tech_adj.tech_challenge_fase3.adapter.controller.exception.ControllerMessagingException;
import br.com.fiap.pos_tech_adj.tech_challenge_fase3.adapter.controller.exception.ControllerNotFoundException;
import br.com.fiap.pos_tech_adj.tech_challenge_fase3.domain.entity.Cliente;
import br.com.fiap.pos_tech_adj.tech_challenge_fase3.domain.entity.Pessoa;
import br.com.fiap.pos_tech_adj.tech_challenge_fase3.domain.repository.ClienteRepository;
import br.com.fiap.pos_tech_adj.tech_challenge_fase3.domain.repository.PessoaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class CadastrarClienteTest {

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private PessoaRepository pessoaRepository;

    @InjectMocks
    private CadastrarCliente cadastrarCliente;

    private Pessoa pessoa;
    private String pessoaId;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        pessoaId = "123";
        pessoa = new Pessoa();
        pessoa.setId(pessoaId);
        pessoa.setNome("Bruno Silva");
        pessoa.setEmail("Bruno.silva@example.com");
        pessoa.setCpf("12345678901");
    }

    @Test
    void deveCadastrarClienteComSucesso() {
        // Simula que a pessoa foi encontrada
        when(pessoaRepository.findById(pessoaId)).thenReturn(Optional.of(pessoa));
        // Simula que não existe cliente associado a essa pessoa
        when(clienteRepository.findByPessoaId(pessoaId)).thenReturn(Optional.empty());
        // Simula o salvamento do cliente
        Cliente cliente = new Cliente();
        cliente.setPessoa(pessoa);
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);


        Cliente clienteCadastrado = cadastrarCliente.execute(pessoaId);
        verify(clienteRepository, times(1)).save(any(Cliente.class));

        // Verifica o resultado
        assertNotNull(clienteCadastrado);
        assertEquals(pessoaId, clienteCadastrado.getPessoa().getId());
    }

    @Test
    void naoDeveCadastrarClienteQuandoPessoaNaoExistir() {
        when(pessoaRepository.findById(pessoaId)).thenReturn(Optional.empty());

        // Verifica se a exceção é lançada
        ControllerNotFoundException exception = assertThrows(ControllerNotFoundException.class, () -> {
            cadastrarCliente.execute(pessoaId);
        });

        assertEquals("Pessoa não encontrada.", exception.getMessage());
    }

    @Test
    void naoDeveCadastrarClienteQuandoJaExistirCliente() {
        when(pessoaRepository.findById(pessoaId)).thenReturn(Optional.of(pessoa));
        when(clienteRepository.findByPessoaId(pessoaId)).thenReturn(Optional.of(new Cliente()));

        // Verifica se a exceção é lançada
        ControllerMessagingException exception = assertThrows(ControllerMessagingException.class, () -> {
            cadastrarCliente.execute(pessoaId);
        });

        assertEquals("Já existe um cliente associado a essa pessoa.", exception.getMessage());
    }
}

