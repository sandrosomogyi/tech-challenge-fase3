package br.com.fiap.pos_tech_adj.tech_challenge_fase3.usecase;

import br.com.fiap.pos_tech_adj.tech_challenge_fase3.adapter.controller.exception.ControllerNotFoundException;
import br.com.fiap.pos_tech_adj.tech_challenge_fase3.domain.entity.Pessoa;
import br.com.fiap.pos_tech_adj.tech_challenge_fase3.domain.repository.PessoaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class GerenciarPessoaTest {

    @Mock
    private PessoaRepository pessoaRepository;

    @InjectMocks
    private GerenciarPessoa gerenciarPessoa;

    private Pessoa pessoa;
    private String pessoaId;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        pessoaId = "123";
        pessoa = new Pessoa();
        pessoa.setId(pessoaId);
        pessoa.setNome("João");
        pessoa.setSobrenome("Silva");
        pessoa.setEmail("joao.silva@example.com");
        pessoa.setCpf("12345678900");
        pessoa.setTelefone("987654321");
        pessoa.setSenha("senha123");
    }

    @Test
    void deveCriarPessoaComSucesso() {
        when(pessoaRepository.save(any(Pessoa.class))).thenReturn(pessoa);

        Pessoa pessoaCriada = gerenciarPessoa.criar(pessoa);

        verify(pessoaRepository, times(1)).save(any(Pessoa.class));
        assertNotNull(pessoaCriada);
        assertEquals(pessoaId, pessoaCriada.getId());
    }

    @Test
    void deveAtualizarPessoaComSucesso() {
        Pessoa pessoaAtualizada = new Pessoa();
        pessoaAtualizada.setNome("Carlos");
        pessoaAtualizada.setEmail("carlos.silva@example.com");

        when(pessoaRepository.findById(pessoaId)).thenReturn(Optional.of(pessoa));
        when(pessoaRepository.save(any(Pessoa.class))).thenReturn(pessoaAtualizada);

        Pessoa resultado = gerenciarPessoa.atualizar(pessoaId, pessoaAtualizada);

        verify(pessoaRepository, times(1)).findById(pessoaId);
        verify(pessoaRepository, times(1)).save(any(Pessoa.class));

        assertNotNull(resultado);
        assertEquals("Carlos", resultado.getNome());
        assertEquals("carlos.silva@example.com", resultado.getEmail());
    }

    @Test
    void naoDeveAtualizarPessoaQuandoNaoExistir() {
        Pessoa pessoaAtualizada = new Pessoa();
        pessoaAtualizada.setNome("Carlos");

        when(pessoaRepository.findById(pessoaId)).thenReturn(Optional.empty());

        ControllerNotFoundException exception = assertThrows(ControllerNotFoundException.class, () -> {
            gerenciarPessoa.atualizar(pessoaId, pessoaAtualizada);
        });

        assertEquals("Pessoa não encontrada.", exception.getMessage());
    }

    @Test
    void deveExcluirPessoaComSucesso() {
        when(pessoaRepository.findById(pessoaId)).thenReturn(Optional.of(pessoa));

        gerenciarPessoa.excluir(pessoaId);

        verify(pessoaRepository, times(1)).findById(pessoaId);
        verify(pessoaRepository, times(1)).deleteById(pessoaId);
    }

    @Test
    void naoDeveExcluirPessoaQuandoNaoExistir() {
        when(pessoaRepository.findById(pessoaId)).thenReturn(Optional.empty());

        ControllerNotFoundException exception = assertThrows(ControllerNotFoundException.class, () -> {
            gerenciarPessoa.excluir(pessoaId);
        });

        assertEquals("Pessoa não encontrada.", exception.getMessage());
    }

    @Test
    void deveBuscarPessoaPorIdComSucesso() {
        when(pessoaRepository.findById(pessoaId)).thenReturn(Optional.of(pessoa));

        Pessoa pessoaEncontrada = gerenciarPessoa.buscarPorId(pessoaId);

        verify(pessoaRepository, times(1)).findById(pessoaId);
        assertNotNull(pessoaEncontrada);
        assertEquals(pessoaId, pessoaEncontrada.getId());
    }

    @Test
    void naoDeveBuscarPessoaPorIdQuandoNaoExistir() {
        when(pessoaRepository.findById(pessoaId)).thenReturn(Optional.empty());

        ControllerNotFoundException exception = assertThrows(ControllerNotFoundException.class, () -> {
            gerenciarPessoa.buscarPorId(pessoaId);
        });

        assertEquals("Pessoa não encontrada.", exception.getMessage());
    }

    @Test
    void deveBuscarTodasAsPessoas() {
        when(pessoaRepository.findAll()).thenReturn(List.of(pessoa));

        List<Pessoa> pessoas = gerenciarPessoa.buscarTodos();

        verify(pessoaRepository, times(1)).findAll();
        assertNotNull(pessoas);
        assertFalse(pessoas.isEmpty());
        assertEquals(1, pessoas.size());
    }
}

