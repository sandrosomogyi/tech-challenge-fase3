package br.com.fiap.pos_tech_adj.tech_challenge_fase3.usecase;

import br.com.fiap.pos_tech_adj.tech_challenge_fase3.domain.entity.Restaurante;
import br.com.fiap.pos_tech_adj.tech_challenge_fase3.domain.repository.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GerenciarRestaurante {

    private final RestauranteRepository restauranteRepository;

    @Autowired
    public GerenciarRestaurante(RestauranteRepository restauranteRepository) {
        this.restauranteRepository = restauranteRepository;
    }

    // Método para buscar todos os restaurantes
    public List<Restaurante> buscarTodos() {
        return restauranteRepository.findAll();
    }

    // Método para buscar todos os restaurantes por Tipo de Cozinha
    public List<Restaurante> buscarTipoDeCozinha(String tipoDeCozinha) {
        return restauranteRepository.findByTipoDeCozinha(tipoDeCozinha);
    }

    // Método para buscar um restaurante por ID
    public Restaurante buscarPorId(String id) {
        return restauranteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Restaurante com ID " + id + " não encontrado."));
    }

    public Restaurante atualizar(String id, Restaurante restaurante) {
        return restauranteRepository.findById(id)
                .map(r -> {
                    r.setNome(restaurante.getNome());
                    r.setLocalizacao(restaurante.getLocalizacao());
                    r.setTipoDeCozinha(restaurante.getTipoDeCozinha());
                    r.setHorarioDeFuncionamento(restaurante.getHorarioDeFuncionamento());
                    r.setCapacidade(restaurante.getCapacidade());
                    return restauranteRepository.save(r);
                })
                .orElseThrow(() -> new IllegalArgumentException("Restaurante não encontrado."));
    }

    public void excluir(String id) {
        Restaurante restaurante = restauranteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Restaurante não encontrado"));

        restauranteRepository.deleteById(id);
    }

}
