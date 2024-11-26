package br.com.fiap.pos_tech_adj.tech_challenge_fase3.domain.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
public class ClienteTest {
    private Cliente cliente;
    private Pessoa pessoa;

    @BeforeEach
    void setUp() {
        cliente = new Cliente();
        pessoa = new Pessoa();
        pessoa.setId("123");
        pessoa.setNome("João");
        pessoa.setSobrenome("Silva");
        pessoa.setCpf("12345678900");
        pessoa.setEmail("joao.silva@example.com");
        pessoa.setTelefone("11999999999");
    }

    @Test
    void testCliente(){
        // Verifica a inicialização padrão do cliente
        assertNull(cliente.getId());
        assertNotNull(cliente.getReservas()); // Lista deve ser inicializada vazia
        assertNotNull(cliente.getAvaliacoes()); // Lista deve ser inicializada vazia
        assertNull(cliente.getPessoa());
        assertNull(cliente.getVersion());
    }

    @Test
    void testAddAvaliacoes() {
        Avaliacao avaliacao = new Avaliacao();
        avaliacao.setId("avaliacao1");
        cliente.getAvaliacoes().add(avaliacao);

        assertEquals(1, cliente.getAvaliacoes().size());
        assertEquals("avaliacao1", cliente.getAvaliacoes().get(0).getId());
    }

    @Test
    void testSetAndGetVersion() {
        cliente.setVersion(2L);
        assertEquals(2L, cliente.getVersion());
    }
}
