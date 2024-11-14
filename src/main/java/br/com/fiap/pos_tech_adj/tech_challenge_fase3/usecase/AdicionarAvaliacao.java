package br.com.fiap.pos_tech_adj.tech_challenge_fase3.usecase;

import br.com.fiap.pos_tech_adj.tech_challenge_fase3.domain.entity.Avaliacao;
import br.com.fiap.pos_tech_adj.tech_challenge_fase3.domain.repository.AvaliacaoRepository;
import br.com.fiap.pos_tech_adj.tech_challenge_fase3.domain.repository.RestauranteRepository;

import java.util.List;

public class AdicionarAvaliacao {

    private final AvaliacaoRepository avaliacaoRepository;
    private final RestauranteRepository restauranteRepository;

    public AdicionarAvaliacao(AvaliacaoRepository avaliacaoRepository, RestauranteRepository restauranteRepository) {
        this.avaliacaoRepository = avaliacaoRepository;
        this.restauranteRepository = restauranteRepository;
    }

    public Avaliacao execute(Avaliacao avaliacao) {
        // Verificar se o restaurante existe
        var restauranteOpt = restauranteRepository.findById(avaliacao.getRestauranteId());
        if (restauranteOpt.isEmpty()) {
            throw new IllegalArgumentException("Restaurante não encontrado.");
        }

        // Verificar se o cliente já fez uma avaliação para este restaurante
        List<Avaliacao> avaliacoesCliente = avaliacaoRepository.findByClienteId(avaliacao.getClienteId());
        boolean avaliacaoExistente = avaliacoesCliente.stream()
                .anyMatch(a -> a.getRestauranteId().equals(avaliacao.getRestauranteId()));

        if (avaliacaoExistente) {
            throw new IllegalArgumentException("O cliente já avaliou este restaurante.");
        }

        // Adicionar a avaliação
        return avaliacaoRepository.save(avaliacao);
    }
}
