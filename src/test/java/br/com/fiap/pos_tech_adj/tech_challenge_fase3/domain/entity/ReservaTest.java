package br.com.fiap.pos_tech_adj.tech_challenge_fase3.domain.entity;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ReservaTest {

    private Reserva reserva;
    private Restaurante restaurante;
    private Cliente cliente;
    private Pessoa pessoa;

    @BeforeEach
    void setUp() {
        // Criando a entidade Pessoa
        pessoa = new Pessoa();
        pessoa.setId("1");
        pessoa.setNome("Cliente Teste");
        pessoa.setEmail("cliente@teste.com");
        pessoa.setTelefone("123456789");

        // Criando a entidade Cliente
        cliente = new Cliente();
        cliente.setId("1");
        cliente.setPessoa(pessoa); // Associação com Pessoa
        cliente.setReservas(new ArrayList<>());
        cliente.setAvaliacoes(new ArrayList<>());

        // Criando a entidade Restaurante
        restaurante = new Restaurante("1", "Restaurante Teste", "Rua Teste", "Italiana", 50);

        // Criando a entidade Reserva
        reserva = new Reserva();
        reserva.setId("1");
        reserva.setRestaurante(restaurante);
        reserva.setCliente(cliente);
        reserva.setDataHoraReserva(LocalDateTime.of(2024, 12, 25, 20, 0));
        reserva.setNumeroDePessoas(4);
    }

    @Test
    void testReservaInitialization() {

        assertEquals("1", reserva.getId());
        assertEquals(restaurante, reserva.getRestaurante());
        assertEquals(cliente, reserva.getCliente());
        assertEquals(LocalDateTime.of(2024, 12, 25, 20, 0), reserva.getDataHoraReserva());
        assertEquals(4, reserva.getNumeroDePessoas());
    }
}