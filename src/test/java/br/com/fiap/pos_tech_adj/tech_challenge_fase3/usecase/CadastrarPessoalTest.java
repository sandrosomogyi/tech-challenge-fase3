package br.com.fiap.pos_tech_adj.tech_challenge_fase3.usecase;

import br.com.fiap.pos_tech_adj.tech_challenge_fase3.adapter.controller.exception.ControllerMessagingException;
import br.com.fiap.pos_tech_adj.tech_challenge_fase3.domain.entity.Pessoa;
import br.com.fiap.pos_tech_adj.tech_challenge_fase3.domain.repository.PessoaRepository;
import br.com.fiap.pos_tech_adj.tech_challenge_fase3.usecase.CadastrarPessoa;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class CadastrarPessoaTest {

    @Mock
    private PessoaRepository pessoaRepository;

    @InjectMocks
    private CadastrarPessoa cadastrarPessoa;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveCadastrarPessoaComSucesso() {
        // Arrange
        Pessoa pessoa = new Pessoa(null, "bruno@gmail.com", "bruno", "Silva", "12345678901", "123456789", "senha123", null);

        when(pessoaRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(pessoaRepository.findByCpf(anyString())).thenReturn(Optional.empty());
        when(pessoaRepository.save(any(Pessoa.class))).thenReturn(pessoa);

        // Act
        Pessoa pessoaCadastrada = cadastrarPessoa.execute(pessoa);

        // Assert
        assertNotNull(pessoaCadastrada);
        assertEquals("bruno", pessoaCadastrada.getNome());
        assertEquals("bruno@gmail.com", pessoaCadastrada.getEmail());
        verify(pessoaRepository, times(1)).save(pessoa);
    }

    @Test
    void deveLancarExcecaoQuandoEmailJaExistir() {
        // Arrange
        Pessoa pessoa = new Pessoa(null, "bruno@gmail.com", "João", "Silva", "12345678901", "123456789", "senha123", null);
        when(pessoaRepository.findByEmail("bruno@gmail.com")).thenReturn(Optional.of(pessoa));

        // Act - Assert
        assertThatThrownBy(() -> cadastrarPessoa.execute(pessoa))
                .isInstanceOf(ControllerMessagingException.class)
                .hasMessage("Já existe uma pessoa com este email.");
    }

    @Test
    void deveLancarExcecaoQuandoCpfJaExistir() {
        // Arrange
        Pessoa pessoa = new Pessoa(null, "bruno@gmail.com", "bruno", "Silva", "12345678901", "123456789", "senha123", null);
        when(pessoaRepository.findByCpf("12345678901")).thenReturn(Optional.of(pessoa));

        // Act & Assert
        assertThatThrownBy(() -> cadastrarPessoa.execute(pessoa))
                .isInstanceOf(ControllerMessagingException.class)
                .hasMessage("Já existe uma pessoa com este CPF.");
    }
}
