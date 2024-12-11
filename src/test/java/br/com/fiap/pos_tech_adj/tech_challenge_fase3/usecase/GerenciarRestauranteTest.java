package br.com.fiap.pos_tech_adj.tech_challenge_fase3.usecase;

import br.com.fiap.pos_tech_adj.tech_challenge_fase3.adapter.controller.exception.ControllerNotFoundException;
import br.com.fiap.pos_tech_adj.tech_challenge_fase3.domain.entity.Restaurante;
import br.com.fiap.pos_tech_adj.tech_challenge_fase3.domain.repository.RestauranteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class GerenciarRestauranteTest {

    @Mock
    private RestauranteRepository restauranteRepository;

    @InjectMocks
    private GerenciarRestaurante gerenciarRestaurante;

    private Restaurante restaurante;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        restaurante = new Restaurante("1", "Restaurante Teste", "São Paulo", "Italiana", 50); // Usando o construtor sem parâmetros
    }

    @Test
    void deveBuscarTodosRestaurantes() {
        // Simula o comportamento do repositório
        when(restauranteRepository.findAll()).thenReturn(List.of(restaurante));

        // Executa o método
        List<Restaurante> restaurantes = gerenciarRestaurante.buscarTodos();

        // Verifica se o repositório foi chamado corretamente
        verify(restauranteRepository, times(1)).findAll();

        // Verifica o resultado
        assertNotNull(restaurantes);
        assertFalse(restaurantes.isEmpty());
        assertEquals(1, restaurantes.size());
        assertEquals("Restaurante Teste", restaurantes.get(0).getNome());
    }

    @Test
    void deveBuscarRestaurantePorId() {
        // Simula o comportamento do repositório
        when(restauranteRepository.findById("1")).thenReturn(Optional.of(restaurante));

        // Executa o método
        Restaurante restauranteEncontrado = gerenciarRestaurante.buscarPorId("1");

        // Verifica se o repositório foi chamado corretamente
        verify(restauranteRepository, times(1)).findById("1");

        // Verifica o resultado
        assertNotNull(restauranteEncontrado);
        assertEquals("Restaurante Teste", restauranteEncontrado.getNome());
    }

    @Test
    void naoDeveBuscarRestaurantePorIdQuandoNaoExistir() {
        // Simula o comportamento do repositório quando o restaurante não for encontrado
        when(restauranteRepository.findById("999")).thenReturn(Optional.empty());

        // Verifica se a exceção é lançada
        ControllerNotFoundException exception = assertThrows(ControllerNotFoundException.class, () -> {
            gerenciarRestaurante.buscarPorId("999");
        });

        // Verifica a mensagem da exceção
        assertEquals("Restaurante com ID 999 não encontrado.", exception.getMessage());
    }

    @Test
    void deveAtualizarRestauranteComSucesso() {
        Restaurante novoRestaurante = new Restaurante("1", "Restaurante Teste", "São Paulo", "Italiana", 50);
        novoRestaurante.setNome("Novo Restaurante");
        novoRestaurante.setLocalizacao("Rio de Janeiro");
        novoRestaurante.setTipoCozinha("Brasileira");
        novoRestaurante.setCapacidade(80);

        // Simula o comportamento do repositório
        when(restauranteRepository.findById("1")).thenReturn(Optional.of(restaurante));
        when(restauranteRepository.save(any(Restaurante.class))).thenReturn(novoRestaurante);

        // Executa o método
        Restaurante restauranteAtualizado = gerenciarRestaurante.atualizar("1", novoRestaurante);

        // Verifica se o repositório foi chamado corretamente
        verify(restauranteRepository, times(1)).save(any(Restaurante.class));

        // Verifica o resultado
        assertNotNull(restauranteAtualizado);
        assertEquals("Novo Restaurante", restauranteAtualizado.getNome());
        assertEquals("Brasileira", restauranteAtualizado.getTipoCozinha());
        assertEquals(80, restauranteAtualizado.getCapacidade());
    }

    @Test
    void naoDeveAtualizarRestauranteQuandoNaoExistir() {
        Restaurante novoRestaurante = new Restaurante("1", "Restaurante Teste", "São Paulo", "Italiana", 50);
        novoRestaurante.setNome("Novo Restaurante");

        // Simula o comportamento do repositório quando o restaurante não for encontrado
        when(restauranteRepository.findById("999")).thenReturn(Optional.empty());

        // Verifica se a exceção é lançada
        ControllerNotFoundException exception = assertThrows(ControllerNotFoundException.class, () -> {
            gerenciarRestaurante.atualizar("999", novoRestaurante);
        });

        // Verifica a mensagem da exceção
        assertEquals("Restaurante não encontrado.", exception.getMessage());
    }

    @Test
    void deveExcluirRestauranteComSucesso() {
        // Simula o comportamento do repositório
        when(restauranteRepository.findById("1")).thenReturn(Optional.of(restaurante));

        // Executa o método
        gerenciarRestaurante.excluir("1");

        // Verifica se o repositório foi chamado corretamente
        verify(restauranteRepository, times(1)).deleteById("1");
    }

    @Test
    void naoDeveExcluirRestauranteQuandoNaoExistir() {
        // Simula o comportamento do repositório quando o restaurante não for encontrado
        when(restauranteRepository.findById("999")).thenReturn(Optional.empty());

        // Verifica se a exceção é lançada
        ControllerNotFoundException exception = assertThrows(ControllerNotFoundException.class, () -> {
            gerenciarRestaurante.excluir("999");
        });

        // Verifica a mensagem da exceção
        assertEquals("Restaurante não encontrado", exception.getMessage());
    }
}

