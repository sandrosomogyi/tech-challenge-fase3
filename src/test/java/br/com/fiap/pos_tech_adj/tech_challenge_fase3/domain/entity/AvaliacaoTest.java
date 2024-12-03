package br.com.fiap.pos_tech_adj.tech_challenge_fase3.domain.entity;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AvaliacaoTest {

    private Avaliacao avaliacao;
    private Restaurante restaurante;
    private Cliente cliente;
    private int nota;

    @BeforeEach
    void setUp() {
        // Configuração de Restaurante
        restaurante = new Restaurante("1", "Restaurante Teste", "Rua Teste", "Italiana", 50);

        // Configuração de Cliente
        cliente = new Cliente();
        cliente.setId("1");

        // Configuração de Avaliacao
        avaliacao = new Avaliacao();
        avaliacao.setId("1");
        avaliacao.setRestaurante(restaurante);
        avaliacao.setCliente(cliente);
        avaliacao.setNota(4);
        avaliacao.setComentario("Excelente comida e atendimento.");
    }

    @Test
    void testAvaliacaoInitialization() {
        assertEquals("1", avaliacao.getId());
        assertEquals(restaurante, avaliacao.getRestaurante());
        assertEquals(cliente, avaliacao.getCliente());
        assertEquals(4, avaliacao.getNota());
        assertEquals("Excelente comida e atendimento.", avaliacao.getComentario());
    }

}
