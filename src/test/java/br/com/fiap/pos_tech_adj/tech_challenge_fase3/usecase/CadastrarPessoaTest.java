package br.com.fiap.pos_tech_adj.tech_challenge_fase3.usecase;

import br.com.fiap.pos_tech_adj.tech_challenge_fase3.domain.entity.Pessoa;
import br.com.fiap.pos_tech_adj.tech_challenge_fase3.domain.repository.PessoaRepository;
import br.com.fiap.pos_tech_adj.tech_challenge_fase3.usecase.CadastrarPessoa;
import br.com.fiap.pos_tech_adj.tech_challenge_fase3.adapter.controller.exception.ControllerMessagingException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class CadastrarPessoaTest {

    AutoCloseable openMocks;
    private CadastrarPessoa cadastrarPessoa;
    @Mock private PessoaRepository pessoaRepository;

    @BeforeEach
    void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
        cadastrarPessoa = new CadastrarPessoa(pessoaRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void deveCadastrarPessoaComSucesso() {
        // Arrange
        Pessoa pessoa = new Pessoa(null, "bruno@gmail.com", "Bruno", "Silva", "12345678901", "123456789", "senha123", null);

        when(pessoaRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(pessoaRepository.findByCpf(anyString())).thenReturn(Optional.empty());
        when(pessoaRepository.save(any(Pessoa.class))).thenReturn(pessoa);

        // Act
        Pessoa pessoaCadastrada = cadastrarPessoa.execute(pessoa);

        // Assert
        assertNotNull(pessoaCadastrada);
        assertEquals("Bruno", pessoaCadastrada.getNome());
        verify(pessoaRepository, times(1)).save(pessoa);
    }

    @Test
    void deveLancarExcecao_QuandoEmailJaExistir() {
        // Arrange
        Pessoa pessoa = new Pessoa(null, "bruno@gmail.com", "João", "Silva", "12345678901", "123456789", "senha123", null);
        when(pessoaRepository.findByEmail("bruno@gmail.com")).thenReturn(Optional.of(pessoa));

        // Act & Assert
        assertThatThrownBy(() -> cadastrarPessoa.execute(pessoa))
                .isInstanceOf(ControllerMessagingException.class)
                .hasMessage("Já existe uma pessoa com este email.");

        verify(pessoaRepository, never()).save(any(Pessoa.class));
    }

    @Test
    void deveLancarExcecao_QuandoCpfJaExistir() {
        // Arrange
        Pessoa pessoa = new Pessoa(null, "bruno@gmail.com", "Bruno", "Silva", "12345678901", "123456789", "senha123", null);
        when(pessoaRepository.findByCpf("12345678901")).thenReturn(Optional.of(pessoa));

        // Act & Assert
        assertThatThrownBy(() -> cadastrarPessoa.execute(pessoa))
                .isInstanceOf(ControllerMessagingException.class)
                .hasMessage("Já existe uma pessoa com este CPF.");

        verify(pessoaRepository, never()).save(any(Pessoa.class));
    }

    @Test
    void deveLancarExcecao_QuandoDadosInvalidos() {
        // Arrange
        Pessoa pessoa = new Pessoa(null, null, "Bruno", "Silva", null, null, null, null);

        // Act & Assert
        assertThatThrownBy(() -> cadastrarPessoa.execute(pessoa))
                .isInstanceOf(ControllerMessagingException.class)
                .hasMessage("O email é obrigatório.");

        verify(pessoaRepository, never()).findByEmail(anyString());
        verify(pessoaRepository, never()).findByCpf(anyString());
        verify(pessoaRepository, never()).save(any(Pessoa.class));
    }

}
