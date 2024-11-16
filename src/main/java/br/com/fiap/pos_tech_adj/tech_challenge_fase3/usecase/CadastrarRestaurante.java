package br.com.fiap.pos_tech_adj.tech_challenge_fase3.usecase;

import br.com.fiap.pos_tech_adj.tech_challenge_fase3.adapter.controller.exception.ControllerMessagingException;
import br.com.fiap.pos_tech_adj.tech_challenge_fase3.domain.Restaurante;
import br.com.fiap.pos_tech_adj.tech_challenge_fase3.domain.repository.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CadastrarRestaurante {

    private final RestauranteRepository restauranteRepository;

    @Autowired
    public CadastrarRestaurante(RestauranteRepository restauranteRepository) {
        this.restauranteRepository = restauranteRepository;
    }

    public Restaurante execute(Restaurante restaurante) {
        // Validação de regras de negócio (ex: nome único, capacidade > 0)
        if (restaurante.getNome() == null || restaurante.getNome().isEmpty()) {
            throw new ControllerMessagingException("Nome do restaurante não pode ser vazio.");
        }
        if (restaurante.getCapacidade() <= 0) {
            throw new ControllerMessagingException("Capacidade do restaurante deve ser maior que zero.");
        }

        return restauranteRepository.save(restaurante);
    }
}
