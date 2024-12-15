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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
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

    @Test
    public void testAdicionarAvaliacao() throws Exception {
        Restaurante newRestaurante =
                new Restaurante(UUID.randomUUID().toString(),"teste", "Rua teste", "Vegana", 100);
        restauranteRepository.save(newRestaurante);

        String novaAvaliacaoJson = String.format(
                "{\"nota\": 5, \"comentario\": \"Excelente!\", \"cliente\": {\"id\": \"%s\"}, \"restaurante\": {\"id\": \"%s\"}}",
                cliente.getId(), newRestaurante.getId());

        mockMvc.perform(post("/api/avaliacoes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(novaAvaliacaoJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nota").value(5))
                .andExpect(jsonPath("$.comentario").value("Excelente!"))
                .andExpect(jsonPath("$.cliente").value(cliente.getId()))
                .andExpect(jsonPath("$.restaurante").value(newRestaurante.getId()));
    }

    @Test
    public void testAtualizarAvaliacao() throws Exception {
        String avaliacaoAtualizadaJson = String.format(
                "{\"nota\": 4, \"comentario\": \"Muito bom!\", \"cliente\": {\"id\": \"%s\"}, \"restaurante\": {\"id\": \"%s\"}}",
                cliente.getId(), restaurante.getId());

        mockMvc.perform(put("/api/avaliacoes/{id}", avaliacao.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(avaliacaoAtualizadaJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nota").value(4))
                .andExpect(jsonPath("$.comentario").value("Muito bom!"))
                .andExpect(jsonPath("$.cliente").value(cliente.getId()))
                .andExpect(jsonPath("$.restaurante").value(restaurante.getId()));
    }

    @Test
    public void testExcluirAvaliacao() throws Exception {
        mockMvc.perform(delete("/api/avaliacoes/{id}", avaliacao.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/avaliacoes/{id}", avaliacao.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
