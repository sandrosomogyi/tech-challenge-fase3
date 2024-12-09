package br.com.fiap.pos_tech_adj.tech_challenge_fase3.repositoy;
import br.com.fiap.pos_tech_adj.tech_challenge_fase3.domain.entity.Cliente;
import br.com.fiap.pos_tech_adj.tech_challenge_fase3.domain.entity.Reserva;
import br.com.fiap.pos_tech_adj.tech_challenge_fase3.domain.entity.Restaurante;
import br.com.fiap.pos_tech_adj.tech_challenge_fase3.domain.repository.ReservaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ReservaRepositoryTest{

    @Mock
    private ReservaRepository reservaRepository;

    @Mock
    private Restaurante restaurante;

    @Mock
    private Cliente cliente;

    private Reserva reserva;

    @BeforeEach
    void setUp() {
        reserva = new Reserva();
        reserva.setId("1");
        reserva.setRestaurante(restaurante);
        reserva.setCliente(cliente);
        reserva.setDataHoraReserva(LocalDateTime.now());
        reserva.setNumeroDePessoas(4);
    }

    @Test
    void deveSalvarReserva() {
        when(reservaRepository.save(reserva)).thenReturn(reserva);

        Reserva savedReserva = reservaRepository.save(reserva);
        assertNotNull(savedReserva);
        assertEquals(reserva.getId(), savedReserva.getId());
        verify(reservaRepository, times(1)).save(reserva);
    }

    @Test
    void deveBuscarReservaPorId() {
        when(reservaRepository.findById("1")).thenReturn(Optional.of(reserva));

        Optional<Reserva> foundReserva = reservaRepository.findById("1");
        assertTrue(foundReserva.isPresent());
        assertEquals(reserva.getId(), foundReserva.get().getId());
        verify(reservaRepository, times(1)).findById("1");
    }

    @Test
    void deveDeletarReserva() {
        doNothing().when(reservaRepository).deleteById("1");
        reservaRepository.deleteById("1");
        verify(reservaRepository, times(1)).deleteById("1");
    }

    @Test
    void deveBuscarReservasPorClienteId() {
        when(reservaRepository.findByClienteId("clienteId")).thenReturn(java.util.List.of(reserva));

        var reservas = reservaRepository.findByClienteId("clienteId");

        assertNotNull(reservas);
        assertFalse(reservas.isEmpty());
        verify(reservaRepository, times(1)).findByClienteId("clienteId");
    }
}
