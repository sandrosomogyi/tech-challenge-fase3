package br.com.fiap.pos_tech_adj.tech_challenge_fase3.usecase;

import br.com.fiap.pos_tech_adj.tech_challenge_fase3.domain.repository.AvaliacaoRepository;
import br.com.fiap.pos_tech_adj.tech_challenge_fase3.domain.repository.ClienteRepository;
import br.com.fiap.pos_tech_adj.tech_challenge_fase3.domain.repository.RestauranteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import br.com.fiap.pos_tech_adj.tech_challenge_fase3.domain.entity.Avaliacao;
import br.com.fiap.pos_tech_adj.tech_challenge_fase3.domain.entity.Cliente;
import br.com.fiap.pos_tech_adj.tech_challenge_fase3.domain.entity.Restaurante;
import br.com.fiap.pos_tech_adj.tech_challenge_fase3.adapter.controller.exception.ControllerNotFoundException;
import java.util.Optional;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import java.util.List;
import java.util.Arrays;


import static org.junit.jupiter.api.Assertions.*;

class GerenciarAvaliacaoTest {

    @Mock
    private AvaliacaoRepository avaliacaoRepository;

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private RestauranteRepository restauranteRepository;

    @InjectMocks
    private GerenciarAvaliacao gerenciarAvaliacao;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void atualizar() {
        String avaliacaoId = "1";
        Avaliacao avaliacao = new Avaliacao();
        avaliacao.setNota(5);
        avaliacao.setComentario("Great!");

        Cliente cliente = new Cliente();
        cliente.setId("1");
        avaliacao.setCliente(cliente);

        Restaurante restaurante = new Restaurante("1","teste", "Rua teste", "Vegana", 100);

        avaliacao.setRestaurante(restaurante);

        Avaliacao savedAvaliacao = new Avaliacao();
        savedAvaliacao.setNota(5);
        savedAvaliacao.setComentario("Great!");

        when(avaliacaoRepository.findById(avaliacaoId)).thenReturn(Optional.of(savedAvaliacao));
        when(avaliacaoRepository.save(any(Avaliacao.class))).thenReturn(savedAvaliacao);
        when(clienteRepository.findById(anyString())).thenReturn(Optional.of(cliente));
        when(restauranteRepository.findById(anyString())).thenReturn(Optional.of(restaurante));

        Avaliacao result = gerenciarAvaliacao.atualizar(avaliacaoId, avaliacao);

        assertNotNull(result);
        assertEquals(avaliacao.getNota(), result.getNota());
        assertEquals(avaliacao.getComentario(), result.getComentario());

        verify(avaliacaoRepository, times(1)).findById(avaliacaoId);
        verify(avaliacaoRepository, times(1)).save(any(Avaliacao.class));
        verify(clienteRepository, times(1)).findById(anyString());
        verify(clienteRepository, times(1)).save(any(Cliente.class));
        verify(restauranteRepository, times(1)).findById(anyString());
        verify(restauranteRepository, times(1)).save(any(Restaurante.class));
    }

    @Test
    void atualizar_AvaliacaoNotFound() {
        String avaliacaoId = "1";
        Avaliacao avaliacao = new Avaliacao();

        when(avaliacaoRepository.findById(avaliacaoId)).thenReturn(Optional.empty());

        assertThrows(ControllerNotFoundException.class, () -> gerenciarAvaliacao.atualizar(avaliacaoId, avaliacao));

        verify(avaliacaoRepository, times(1)).findById(avaliacaoId);
        verify(avaliacaoRepository, never()).save(any(Avaliacao.class));
        verify(clienteRepository, never()).findById(anyString());
        verify(clienteRepository, never()).save(any(Cliente.class));
        verify(restauranteRepository, never()).findById(anyString());
        verify(restauranteRepository, never()).save(any(Restaurante.class));
    }

    void excluir() {
        String avaliacaoId = "1";
        Avaliacao avaliacao = new Avaliacao();
        avaliacao.setId(avaliacaoId);

        Cliente cliente = new Cliente();
        cliente.setId("1");
        avaliacao.setCliente(cliente);

        Restaurante restaurante = new Restaurante("1", "teste", "Rua teste", "Vegana", 100);
        avaliacao.setRestaurante(restaurante);

        when(avaliacaoRepository.findById(avaliacaoId)).thenReturn(Optional.of(avaliacao));
        when(clienteRepository.findById(anyString())).thenReturn(Optional.of(cliente));
        when(restauranteRepository.findById(anyString())).thenReturn(Optional.of(restaurante));

        gerenciarAvaliacao.excluir(avaliacaoId);

        verify(avaliacaoRepository, times(1)).findById(avaliacaoId);
        verify(avaliacaoRepository, times(1)).deleteById(avaliacaoId);
        verify(clienteRepository, times(1)).findById(anyString());
        verify(clienteRepository, times(1)).save(any(Cliente.class));
        verify(restauranteRepository, times(1)).findById(anyString());
        verify(restauranteRepository, times(1)).save(any(Restaurante.class));
    }

    @Test
    public void testExcluirAvaliacaoNaoExistente() {
        String avaliacaoId = "avaliacaoId";

        when(avaliacaoRepository.findById(avaliacaoId)).thenReturn(Optional.empty());

        assertThrows(ControllerNotFoundException.class, () -> gerenciarAvaliacao.excluir(avaliacaoId));
    }

    @Test
    public void testBuscarPorIdExistente() {
        String avaliacaoId = "avaliacaoId";
        Avaliacao avaliacao = new Avaliacao();

        when(avaliacaoRepository.findById(avaliacaoId)).thenReturn(Optional.of(avaliacao));

        Avaliacao result = gerenciarAvaliacao.buscarPorId(avaliacaoId);

        assertNotNull(result);
        assertEquals(avaliacao, result);
    }

    @Test
    public void testBuscarPorIdNaoExistente() {
        String avaliacaoId = "avaliacaoId";

        when(avaliacaoRepository.findById(avaliacaoId)).thenReturn(Optional.empty());

        assertThrows(ControllerNotFoundException.class, () -> gerenciarAvaliacao.buscarPorId(avaliacaoId));
    }

    @Test
    public void testBuscarTodas() {
        Avaliacao avaliacao1 = new Avaliacao();
        Avaliacao avaliacao2 = new Avaliacao();
        List<Avaliacao> avaliacoes = Arrays.asList(avaliacao1, avaliacao2);

        when(avaliacaoRepository.findAll()).thenReturn(avaliacoes);

        List<Avaliacao> result = gerenciarAvaliacao.buscarTodas();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains(avaliacao1));
        assertTrue(result.contains(avaliacao2));
    }
}