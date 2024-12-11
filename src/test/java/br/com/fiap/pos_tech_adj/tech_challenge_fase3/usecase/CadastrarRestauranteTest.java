package br.com.fiap.pos_tech_adj.tech_challenge_fase3.usecase;

import br.com.fiap.pos_tech_adj.tech_challenge_fase3.adapter.controller.exception.ControllerMessagingException;
import br.com.fiap.pos_tech_adj.tech_challenge_fase3.domain.entity.Restaurante;
import br.com.fiap.pos_tech_adj.tech_challenge_fase3.domain.repository.RestauranteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CadastrarRestauranteTest {

    @Mock
    private RestauranteRepository restauranteRepository;

    private CadastrarRestaurante cadastrarRestaurante;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        cadastrarRestaurante = new CadastrarRestaurante(restauranteRepository);
    }
    @Test
    void deveLancarExcecaoQuandoCapacidadeForMenorOuIgualAZero() {
        // Arrange
        Restaurante restauranteComCapacidadeZero = new Restaurante(null, "Restaurante Teste", "Endereco Teste", "Brasileira", 0);
        Restaurante restauranteComCapacidadeNegativa = new Restaurante(null, "Restaurante Teste", "Endereco Teste", "Brasileira", -1);

        // Execução e Verificação: Testando com capacidade zero
        assertThatThrownBy(() -> cadastrarRestaurante.execute(restauranteComCapacidadeZero))
                .isInstanceOf(ControllerMessagingException.class)
                .hasMessage("Capacidade do restaurante deve ser maior que zero.");

        // Execução e Verificação: Testando com capacidade negativa
        assertThatThrownBy(() -> cadastrarRestaurante.execute(restauranteComCapacidadeNegativa))
                .isInstanceOf(ControllerMessagingException.class)
                .hasMessage("Capacidade do restaurante deve ser maior que zero.");
    }

    @Test
    void deveCadastrarRestauranteComSucesso() {
        // Arrange
        // Criando o restaurante com todos os parâmetros necessários
        Restaurante restaurante = new Restaurante(null, "Restaurante Teste", "Endereco Teste", "Brasileira", 50);

        // Simulando o comportamento do repositório
        when(restauranteRepository.save(any(Restaurante.class))).thenReturn(restaurante);

        // Act
        Restaurante restauranteCadastrado = cadastrarRestaurante.execute(restaurante);

        // Assert
        assertNotNull(restauranteCadastrado);  // Verificando se o restaurante foi salvo com sucesso
        assertEquals("Restaurante Teste", restauranteCadastrado.getNome());  // Verificando o nome
        assertEquals("Endereco Teste", restauranteCadastrado.getLocalizacao());  // Verificando a localização
        assertEquals(50, restauranteCadastrado.getCapacidade());  // Verificando a capacidade
        verify(restauranteRepository, times(1)).save(restaurante);  // Verificando se o método save foi chamado uma vez
    }


}
