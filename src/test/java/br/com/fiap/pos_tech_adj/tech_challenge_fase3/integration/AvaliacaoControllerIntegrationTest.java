package br.com.fiap.pos_tech_adj.tech_challenge_fase3.integration;

import br.com.fiap.pos_tech_adj.tech_challenge_fase3.domain.entity.Avaliacao;
import br.com.fiap.pos_tech_adj.tech_challenge_fase3.domain.entity.Cliente;
import br.com.fiap.pos_tech_adj.tech_challenge_fase3.domain.entity.Restaurante;
import br.com.fiap.pos_tech_adj.tech_challenge_fase3.domain.repository.AvaliacaoRepository;
import br.com.fiap.pos_tech_adj.tech_challenge_fase3.domain.repository.ClienteRepository;
import br.com.fiap.pos_tech_adj.tech_challenge_fase3.domain.repository.RestauranteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class AvaliacaoControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AvaliacaoRepository avaliacaoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private RestauranteRepository restauranteRepository;

    private Avaliacao avaliacao;
    private Cliente cliente;
    private Restaurante restaurante;

    @BeforeEach
    public void setUp() {
        cliente = new Cliente();
        cliente.setId(UUID.randomUUID().toString());
        clienteRepository.save(cliente);

        restaurante = new Restaurante(UUID.randomUUID().toString(),"teste", "Rua teste", "Vegana", 100);
        restauranteRepository.save(restaurante);

        avaliacao = new Avaliacao();
        avaliacao.setId(UUID.randomUUID().toString());
        avaliacao.setCliente(cliente);
        avaliacao.setRestaurante(restaurante);
        avaliacaoRepository.save(avaliacao);
    }

    @Test
    public void testBuscarPorId() throws Exception {
        mockMvc.perform(get("/api/avaliacoes/{id}", avaliacao.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(avaliacao.getId()))
                .andExpect(jsonPath("$.cliente").value(cliente.getId()))
                .andExpect(jsonPath("$.restaurante").value(restaurante.getId()));
    }
}
