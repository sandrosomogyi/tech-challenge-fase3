package br.com.fiap.pos_tech_adj.tech_challenge_fase3.usecase;

import br.com.fiap.pos_tech_adj.tech_challenge_fase3.adapter.controller.exception.ControllerNotFoundException;
import br.com.fiap.pos_tech_adj.tech_challenge_fase3.domain.entity.Cliente;
import br.com.fiap.pos_tech_adj.tech_challenge_fase3.domain.entity.Pessoa;
import br.com.fiap.pos_tech_adj.tech_challenge_fase3.domain.repository.ClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

class GerenciarClienteTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private GerenciarCliente gerenciarCliente;

    private Cliente cliente;
    private Pessoa pessoa;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Criar um objeto Pessoa para o cliente
        pessoa = new Pessoa("1", "joao@example.com", "João", "Silva", "12345678901", "123456789", "senha123", null);

        // Criar o cliente com os parâmetros necessários
        cliente = new Cliente("1", null, null, pessoa, null);
    }

    @Test
    void deveCriarClienteComSucesso() {
        // Arrange
        when(clienteRepository.save(cliente)).thenReturn(cliente);

        // Act
        Cliente clienteCadastrado = gerenciarCliente.criar(cliente);

        // Assert
        assertThat(clienteCadastrado).isNotNull();
        assertThat(clienteCadastrado.getId()).isEqualTo("1");
        verify(clienteRepository, times(1)).save(cliente);
    }

    @Test
    void deveAtualizarClienteComSucesso() {
        // Arrange
        Cliente clienteAtualizado = new Cliente("1", null, null, pessoa, null);
        when(clienteRepository.findById("1")).thenReturn(Optional.of(cliente));
        when(clienteRepository.save(cliente)).thenReturn(clienteAtualizado);

        // Act
        Cliente clienteResult = gerenciarCliente.atualizar("1", clienteAtualizado);

        // Assert
        assertThat(clienteResult).isNotNull();
        assertThat(clienteResult.getId()).isEqualTo("1");
        verify(clienteRepository, times(1)).findById("1");
        verify(clienteRepository, times(1)).save(cliente);
    }

    @Test
    void deveLancarExcecaoQuandoClienteNaoForEncontradoParaAtualizar() {
        // Arrange
        Cliente clienteAtualizado = new Cliente("1", null, null, pessoa, null);
        when(clienteRepository.findById("1")).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> gerenciarCliente.atualizar("1", clienteAtualizado))
                .isInstanceOf(ControllerNotFoundException.class)
                .hasMessage("Cliente não encontrado.");
    }

    @Test
    void deveExcluirClienteComSucesso() {
        // Arrange
        when(clienteRepository.findById("1")).thenReturn(Optional.of(cliente));

        // Act
        gerenciarCliente.excluir("1");

        // Assert
        verify(clienteRepository, times(1)).deleteById("1");
    }

    @Test
    void deveLancarExcecaoQuandoClienteNaoForEncontradoParaExcluir() {
        // Arrange
        when(clienteRepository.findById("1")).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> gerenciarCliente.excluir("1"))
                .isInstanceOf(ControllerNotFoundException.class)
                .hasMessage("Cliente não encontrado.");
    }

    @Test
    void deveBuscarClientePorIdComSucesso() {
        // Arrange
        when(clienteRepository.findById("1")).thenReturn(Optional.of(cliente));

        // Act
        Cliente clienteResult = gerenciarCliente.buscarPorId("1");

        // Assert
        assertThat(clienteResult).isNotNull();
        assertThat(clienteResult.getId()).isEqualTo("1");
        verify(clienteRepository, times(1)).findById("1");
    }

    @Test
    void deveLancarExcecaoQuandoClienteNaoForEncontradoPorId() {
        // Arrange
        when(clienteRepository.findById("1")).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> gerenciarCliente.buscarPorId("1"))
                .isInstanceOf(ControllerNotFoundException.class)
                .hasMessage("Cliente não encontrado.");
    }

    @Test
    void deveBuscarTodosClientesComSucesso() {
        // Arrange
        when(clienteRepository.findAll()).thenReturn(List.of(cliente));

        // Act
        List<Cliente> clientes = gerenciarCliente.buscarTodos();

        // Assert
        assertThat(clientes).isNotEmpty();
        assertThat(clientes.get(0).getId()).isEqualTo("1");
        verify(clienteRepository, times(1)).findAll();
    }
}
