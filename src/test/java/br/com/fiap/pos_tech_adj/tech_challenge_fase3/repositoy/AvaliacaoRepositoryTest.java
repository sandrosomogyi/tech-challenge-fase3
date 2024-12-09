package br.com.fiap.pos_tech_adj.tech_challenge_fase3.repositoy;

import br.com.fiap.pos_tech_adj.tech_challenge_fase3.domain.entity.Avaliacao;
import br.com.fiap.pos_tech_adj.tech_challenge_fase3.domain.entity.Cliente;
import br.com.fiap.pos_tech_adj.tech_challenge_fase3.domain.entity.Restaurante;
import br.com.fiap.pos_tech_adj.tech_challenge_fase3.domain.repository.AvaliacaoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

class AvaliacaoRepositoryTest {

    @Mock
    private AvaliacaoRepository avaliacaoRepository;

    @InjectMocks
    private Avaliacao avaliacao;

    private String id = "1";
    private String restauranteId = "restaurante-123";
    private String clienteId = "cliente-456";
    private Restaurante restaurante;
    private Cliente cliente;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Instanciando Restaurante com parâmetros
        restaurante = new Restaurante(restauranteId, "Restaurante A", "Rua A", "Comida Italiana", 10);

        // Criando o Cliente com o ID correto
        cliente = new Cliente();
        cliente.setId(clienteId);

        // Criando a instância de Avaliacao
        avaliacao = new Avaliacao();
        avaliacao.setId(id);
        avaliacao.setRestaurante(restaurante);
        avaliacao.setCliente(cliente);
        avaliacao.setNota(4);
        avaliacao.setComentario("Excelente comida!");
    }

    @Test
    void deveSalvarAvaliacao() {
        when(avaliacaoRepository.save(avaliacao)).thenReturn(avaliacao);

        Avaliacao savedAvaliacao = avaliacaoRepository.save(avaliacao);

        assertNotNull(savedAvaliacao);
        assertEquals(id, savedAvaliacao.getId());
        assertEquals(restauranteId, savedAvaliacao.getRestaurante().getId());
        assertEquals(clienteId, savedAvaliacao.getCliente().getId());
        assertEquals(4, savedAvaliacao.getNota());
        assertEquals("Excelente comida!", savedAvaliacao.getComentario());
        verify(avaliacaoRepository, times(1)).save(avaliacao);
    }

    @Test
    void deveBuscarAvaliacaoPorId() {
        when(avaliacaoRepository.findById(id)).thenReturn(Optional.of(avaliacao));

        Optional<Avaliacao> foundAvaliacao = avaliacaoRepository.findById(id);

        assertTrue(foundAvaliacao.isPresent());
        assertEquals(id, foundAvaliacao.get().getId());
        assertEquals(restauranteId, foundAvaliacao.get().getRestaurante().getId());
        assertEquals(clienteId, foundAvaliacao.get().getCliente().getId());
        verify(avaliacaoRepository, times(1)).findById(id);
    }

    @Test
    void deveDeletarAvaliacao() {
        doNothing().when(avaliacaoRepository).deleteById(id);
        avaliacaoRepository.deleteById(id);
        verify(avaliacaoRepository, times(1)).deleteById(id);
    }
}
