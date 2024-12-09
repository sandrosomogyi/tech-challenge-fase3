package br.com.fiap.pos_tech_adj.tech_challenge_fase3.repositoy;
import br.com.fiap.pos_tech_adj.tech_challenge_fase3.domain.entity.Restaurante;
import br.com.fiap.pos_tech_adj.tech_challenge_fase3.domain.repository.RestauranteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class RestauranteRepositoryTest {

    private RestauranteRepository restauranteRepository;

    @BeforeEach
    void setUp() {

        restauranteRepository = Mockito.mock(RestauranteRepository.class);
    }
    @Test
    void deveRetornarRestaurantePorId() {
        // Dados de entrada
        String idRestaurante = "1";
        Restaurante restaurante = new Restaurante("1", "Restaurante A", "Brasileira", "São Paulo", 100);
        when(restauranteRepository.findById(idRestaurante)).thenReturn(Optional.of(restaurante));

        // Execução
        Optional<Restaurante> resultado = restauranteRepository.findById(idRestaurante);

        // Verificações
        assertThat(resultado).isPresent();
        assertThat(resultado.get().getNome()).isEqualTo("Restaurante A");
        verify(restauranteRepository, times(1)).findById(idRestaurante);
    }
    @Test
    void deveRetornarVazioQuandoRestauranteNaoExistir() {
        // Dados de entrada
        String idRestaurante = "99";
        when(restauranteRepository.findById(idRestaurante)).thenReturn(Optional.empty());

        // Execução
        Optional<Restaurante> resultado = restauranteRepository.findById(idRestaurante);

        // Verificações
        assertThat(resultado).isNotPresent();
        verify(restauranteRepository, times(1)).findById(idRestaurante);
    }
    @Test
    void deveSalvarRestaurante() {
        // Dados de entrada
        Restaurante novoRestaurante = new Restaurante(null, "Restaurante Novo", "Italiana", "Curitiba", 80);
        Restaurante restauranteSalvo = new Restaurante("3", "Restaurante Novo", "Italiana", "Curitiba", 80);
        when(restauranteRepository.save(novoRestaurante)).thenReturn(restauranteSalvo);

        // Execução
        Restaurante resultado = restauranteRepository.save(novoRestaurante);

        // Verificações
        assertThat(resultado.getId()).isEqualTo("3");
        assertThat(resultado.getNome()).isEqualTo("Restaurante Novo");
        verify(restauranteRepository, times(1)).save(novoRestaurante);
    }
    @Test
    void deveExcluirRestaurantePorId() {
        // Dados de entrada
        String idRestaurante = "1";
        doNothing().when(restauranteRepository).deleteById(idRestaurante);

        // Execução
        restauranteRepository.deleteById(idRestaurante);

        // Verificações
        verify(restauranteRepository, times(1)).deleteById(idRestaurante);
    }




}
