package br.com.fiap.pos_tech_adj.tech_challenge_fase3.domain.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AdministradorTest {

    private Administrador administrador;
    private Pessoa pessoa;

    @BeforeEach
    void setUp() {
        // Configuração de Pessoa
        pessoa = new Pessoa();
        pessoa.setId("1");
        pessoa.setNome("Administrador Teste");
        pessoa.setEmail("admin@teste.com");
        pessoa.setTelefone("123456789");

        // Configuração de Administrador
        administrador = new Administrador();
        administrador.setId("1");
        administrador.setPessoa(pessoa);
        administrador.setRestaurantes(new ArrayList<>());
    }

    @Test
    void testAdministradorInitialization() {
        // Verifica se os valores foram atribuídos corretamente
        assertEquals("1", administrador.getId());
        assertEquals(pessoa, administrador.getPessoa());
        assertEquals(0, administrador.getRestaurantes().size()); // Lista de restaurantes vazia
    }
}

