package br.com.fiap.pos_tech_adj.tech_challenge_fase3.usecase;

import br.com.fiap.pos_tech_adj.tech_challenge_fase3.adapter.controller.exception.ControllerMessagingException;
import br.com.fiap.pos_tech_adj.tech_challenge_fase3.adapter.controller.exception.ControllerNotFoundException;
import br.com.fiap.pos_tech_adj.tech_challenge_fase3.domain.entity.Cliente;
import br.com.fiap.pos_tech_adj.tech_challenge_fase3.domain.entity.Restaurante;
import br.com.fiap.pos_tech_adj.tech_challenge_fase3.domain.entity.Reserva;
import br.com.fiap.pos_tech_adj.tech_challenge_fase3.domain.repository.ClienteRepository;
import br.com.fiap.pos_tech_adj.tech_challenge_fase3.domain.repository.ReservaRepository;
import br.com.fiap.pos_tech_adj.tech_challenge_fase3.domain.repository.RestauranteRepository;
import br.com.fiap.pos_tech_adj.tech_challenge_fase3.usecase.CriarReserva;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class CriarReservaTest {

    @Mock
    private ReservaRepository reservaRepository;

    @Mock
    private RestauranteRepository restauranteRepository;

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private CriarReserva criarReserva;

    private Reserva reserva;
    private Restaurante restaurante;
    private Cliente cliente;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Inicializa o restaurante com os parâmetros necessários para o construtor
        restaurante = new Restaurante("Restaurante Teste", "Endereço Teste", "123456789", "Italiana", 100);
        cliente = new Cliente();
        cliente.setId("1");

        reserva = new Reserva();
        reserva.setRestaurante(restaurante);
        reserva.setCliente(cliente);
        reserva.setNumeroDePessoas(2);
        reserva.setDataHoraReserva(LocalDateTime.now());
    }

    @Test
    void deveCriarReservaComSucesso() {
        when(restauranteRepository.findById(restaurante.getId())).thenReturn(Optional.of(restaurante));
        when(clienteRepository.findById(cliente.getId())).thenReturn(Optional.of(cliente));
        when(reservaRepository.findByDataHoraReservaBetween(any(), any())).thenReturn(List.of());
        when(reservaRepository.save(any(Reserva.class))).thenReturn(reserva);

        Reserva reservaCriada = criarReserva.execute(reserva);

        assertNotNull(reservaCriada);
        assertEquals(restaurante.getId(), reservaCriada.getRestaurante().getId());
        assertEquals(cliente.getId(), reservaCriada.getCliente().getId());
    }

    @Test
    void naoDeveCriarReservaQuandoRestauranteNaoExistir() {
        when(restauranteRepository.findById(restaurante.getId())).thenReturn(Optional.empty());

        ControllerNotFoundException exception = assertThrows(ControllerNotFoundException.class, () -> {
            criarReserva.execute(reserva);
        });

        assertEquals("Restaurante não encontrado.", exception.getMessage());
    }

    @Test
    void naoDeveCriarReservaQuandoClienteNaoExistir() {
        when(restauranteRepository.findById(restaurante.getId())).thenReturn(Optional.of(restaurante));
        when(clienteRepository.findById(cliente.getId())).thenReturn(Optional.empty());

        ControllerNotFoundException exception = assertThrows(ControllerNotFoundException.class, () -> {
            criarReserva.execute(reserva);
        });

        assertEquals("Cliente não encontrado.", exception.getMessage());
    }

}
