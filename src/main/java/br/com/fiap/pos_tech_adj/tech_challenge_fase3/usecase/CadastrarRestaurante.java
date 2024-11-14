package br.com.fiap.pos_tech_adj.tech_challenge_fase3.usecase;

import br.com.fiap.pos_tech_adj.tech_challenge_fase3.domain.entity.Restaurante;
import br.com.fiap.pos_tech_adj.tech_challenge_fase3.domain.repository.RestauranteRepository;

import java.util.List;

public class CadastrarRestaurante {

    private final RestauranteRepository restauranteRepository;

    public CadastrarRestaurante(RestauranteRepository restauranteRepository) {
        this.restauranteRepository = restauranteRepository;
    }

    public Restaurante execute(Restaurante restaurante) {
        // Validação de regras de negócio (ex: nome único, capacidade > 0)
        if (restaurante.getNome() == null || restaurante.getNome().isEmpty()) {
            throw new IllegalArgumentException("Nome do restaurante não pode ser vazio.");
        }
        if (restaurante.getCapacidade() <= 0) {
            throw new IllegalArgumentException("Capacidade do restaurante deve ser maior que zero.");
        }

        return restauranteRepository.save(restaurante);
    }

    // Método para buscar todos os restaurantes
    public List<Restaurante> buscarTodos() {
        return restauranteRepository.findAll();
    }
}