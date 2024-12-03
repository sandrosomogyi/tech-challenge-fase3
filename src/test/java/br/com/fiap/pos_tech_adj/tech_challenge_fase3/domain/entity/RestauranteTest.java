package br.com.fiap.pos_tech_adj.tech_challenge_fase3.domain.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;  // Importação necessária

class RestauranteTest {

    private Restaurante restaurante;

    @BeforeEach
    void setUp() {
        // Inicializa um novo objeto Restaurante antes de cada teste
        restaurante = new Restaurante("1", "Restaurante Teste", "Rua Teste", "Italiana", 50);
    }
    @Test
    void testRestauranteInitialization() {
        // Verifica se os valores foram corretamente atribuídos
        assertEquals("1", restaurante.getId());  // Verifica o ID
        assertEquals("Restaurante Teste", restaurante.getNome());  // Verifica o nome
        assertEquals("Rua Teste", restaurante.getLocalizacao());  // Verifica a localização
        assertEquals("Italiana", restaurante.getTipoCozinha());  // Verifica o tipo de cozinha
        assertEquals(50, restaurante.getCapacidade());  // Verifica a capacidade
    }
    @Test
    void testGettersSetters() {
        restaurante.setId("2");
        restaurante.setNome("Restaurante Atualizado");
        restaurante.setLocalizacao("Rua Atualizada");
        restaurante.setTipoCozinha("Francesa");
        restaurante.setCapacidade(100);

        // Verificando se os setters atualizaram os valores corretamente
        assertEquals("2", restaurante.getId());
        assertEquals("Restaurante Atualizado", restaurante.getNome());
        assertEquals("Rua Atualizada", restaurante.getLocalizacao());
        assertEquals("Francesa", restaurante.getTipoCozinha());
        assertEquals(100, restaurante.getCapacidade());

    }


}
