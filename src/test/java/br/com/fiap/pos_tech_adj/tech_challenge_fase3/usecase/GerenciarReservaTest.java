package br.com.fiap.pos_tech_adj.tech_challenge_fase3.usecase;


import br.com.fiap.pos_tech_adj.tech_challenge_fase3.adapter.controller.exception.ControllerNotFoundException;
import br.com.fiap.pos_tech_adj.tech_challenge_fase3.domain.entity.Cliente;
import br.com.fiap.pos_tech_adj.tech_challenge_fase3.domain.entity.Reserva;
import br.com.fiap.pos_tech_adj.tech_challenge_fase3.domain.entity.Restaurante;
import br.com.fiap.pos_tech_adj.tech_challenge_fase3.domain.repository.ClienteRepository;
import br.com.fiap.pos_tech_adj.tech_challenge_fase3.domain.repository.ReservaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

class GerenciarReservaTest {

    @Mock
    private ReservaRepository reservaRepository;

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private GerenciarReserva gerenciarReserva;

    private Reserva reserva;
    private Cliente cliente;
    private Restaurante restaurante;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Inicializando cliente e restaurante
        cliente = new Cliente("1", null, null, null, null);
        restaurante = new Restaurante("1", "Restaurante Exemplo", "Endereço", "Brasileira", 50);

        // Inicializando reserva
        reserva = new Reserva();
        reserva.setId("1");
        reserva.setRestaurante(restaurante);
        reserva.setCliente(cliente);
        reserva.setDataHoraReserva(LocalDateTime.now());
        reserva.setNumeroDePessoas(4);
    }

    @Test
    void deveCriarReservaComSucesso() {
        // Arrange
        when(reservaRepository.save(reserva)).thenReturn(reserva);

        // Act
        Reserva reservaCriada = gerenciarReserva.criar(reserva);

        // Assert
        assertThat(reservaCriada).isNotNull();
        assertThat(reservaCriada.getId()).isEqualTo("1");
        assertThat(reservaCriada.getNumeroDePessoas()).isEqualTo(4);
        verify(reservaRepository, times(1)).save(reserva);
    }

    @Test
    void deveLancarExcecaoAoAtualizarReservaQueNaoExiste() {
        // Arrange
        Reserva reservaAtualizada = new Reserva();
        reservaAtualizada.setId("1");
        reservaAtualizada.setRestaurante(restaurante);
        reservaAtualizada.setCliente(cliente);
        reservaAtualizada.setDataHoraReserva(LocalDateTime.now().plusHours(1));
        reservaAtualizada.setNumeroDePessoas(6);

        when(reservaRepository.findById("1")).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> gerenciarReserva.atualizar("1", reservaAtualizada))
                .isInstanceOf(ControllerNotFoundException.class)
                .hasMessage("Reserva não encontrada.");
    }

    @Test
    void deveLancarExcecaoAoExcluirReservaQueNaoExiste() {
        // Arrange
        when(reservaRepository.findById("1")).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> gerenciarReserva.excluir("1"))
                .isInstanceOf(ControllerNotFoundException.class)
                .hasMessage("Reserva não encontrada.");
    }

    @Test
    void deveBuscarReservaPorIdComSucesso() {
        // Arrange
        when(reservaRepository.findById("1")).thenReturn(Optional.of(reserva));

        // Act
        Reserva reservaEncontrada = gerenciarReserva.buscarPorId("1");

        // Assert
        assertThat(reservaEncontrada).isNotNull();
        assertThat(reservaEncontrada.getId()).isEqualTo("1");
        verify(reservaRepository, times(1)).findById("1");
    }

    @Test
    void deveLancarExcecaoAoBuscarReservaQueNaoExiste() {
        // Arrange
        when(reservaRepository.findById("1")).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> gerenciarReserva.buscarPorId("1"))
                .isInstanceOf(ControllerNotFoundException.class)
                .hasMessage("Reserva não encontrada.");
    }
}

