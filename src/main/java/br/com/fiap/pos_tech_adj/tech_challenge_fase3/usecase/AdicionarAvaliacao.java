package br.com.fiap.pos_tech_adj.tech_challenge_fase3.usecase;

import br.com.fiap.pos_tech_adj.tech_challenge_fase3.adapter.controller.exception.ControllerMessagingException;
import br.com.fiap.pos_tech_adj.tech_challenge_fase3.adapter.controller.exception.ControllerNotFoundException;
import br.com.fiap.pos_tech_adj.tech_challenge_fase3.domain.entity.Avaliacao;
import br.com.fiap.pos_tech_adj.tech_challenge_fase3.domain.repository.AvaliacaoRepository;
import br.com.fiap.pos_tech_adj.tech_challenge_fase3.domain.repository.ClienteRepository;
import br.com.fiap.pos_tech_adj.tech_challenge_fase3.domain.repository.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdicionarAvaliacao {

    private final AvaliacaoRepository avaliacaoRepository;
    private final RestauranteRepository restauranteRepository;
    private final ClienteRepository clienteRepository;

    @Autowired
    public AdicionarAvaliacao(AvaliacaoRepository avaliacaoRepository, RestauranteRepository restauranteRepository, ClienteRepository clienteRepository) {
        this.avaliacaoRepository = avaliacaoRepository;
        this.restauranteRepository = restauranteRepository;
        this.clienteRepository = clienteRepository;
    }

    public Avaliacao execute(Avaliacao avaliacao) {
        // Verificar se o restaurante existe
        var restauranteOpt = restauranteRepository.findById(avaliacao.getRestaurante().getId());
        if (restauranteOpt.isEmpty()) {
            throw new ControllerNotFoundException("Restaurante não encontrado.");
        }

        avaliacao.setRestaurante(restauranteOpt.get());

        // Verificar se o cliente existe
        var clienteOpt = clienteRepository.findById(avaliacao.getCliente().getId());
        if (clienteOpt.isEmpty()) {
            throw new ControllerNotFoundException("Restaurante não encontrado.");
        }

        avaliacao.setCliente(clienteOpt.get());

        // Verificar se o cliente já fez uma avaliação para este restaurante
        List<Avaliacao> avaliacoesCliente = avaliacaoRepository.findByClienteId(avaliacao.getCliente().getId());
        boolean avaliacaoExistente = avaliacoesCliente.stream()
                .anyMatch(a -> a.getRestaurante().equals(avaliacao.getRestaurante()));

        if (avaliacaoExistente) {
            throw new ControllerMessagingException("O cliente já avaliou este restaurante.");
        }

        // Adicionar a avaliação
        var avaliacaoSaved = avaliacaoRepository.save(avaliacao);

        restauranteOpt.get().getAvaliacoes().add(avaliacaoSaved);
        restauranteRepository.save(restauranteOpt.get());

        clienteOpt.get().getAvaliacoes().add(avaliacaoSaved);
        clienteRepository.save(clienteOpt.get());

        return avaliacaoSaved;
    }
}
