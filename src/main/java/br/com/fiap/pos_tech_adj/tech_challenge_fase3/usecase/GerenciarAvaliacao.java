package br.com.fiap.pos_tech_adj.tech_challenge_fase3.usecase;

import br.com.fiap.pos_tech_adj.tech_challenge_fase3.adapter.controller.exception.ControllerNotFoundException;
import br.com.fiap.pos_tech_adj.tech_challenge_fase3.domain.entity.Avaliacao;
import br.com.fiap.pos_tech_adj.tech_challenge_fase3.domain.repository.AvaliacaoRepository;
import br.com.fiap.pos_tech_adj.tech_challenge_fase3.domain.repository.ClienteRepository;
import br.com.fiap.pos_tech_adj.tech_challenge_fase3.domain.repository.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GerenciarAvaliacao {

    private final AvaliacaoRepository avaliacaoRepository;
    private final ClienteRepository clienteRepository;
    private  final RestauranteRepository restauranteRepository;

    @Autowired
    public GerenciarAvaliacao(AvaliacaoRepository avaliacaoRepository, ClienteRepository clienteRepository, RestauranteRepository restauranteRepository) {
        this.avaliacaoRepository = avaliacaoRepository;
        this.clienteRepository = clienteRepository;
        this.restauranteRepository = restauranteRepository;
    }

    public Avaliacao atualizar(String id, Avaliacao avaliacao) {
        var avaliacaoSaved = avaliacaoRepository.findById(id)
                .map(a -> {
                    a.setNota(avaliacao.getNota());
                    a.setComentario(avaliacao.getComentario());
                    return avaliacaoRepository.save(a);
                })
                .orElseThrow(() -> new ControllerNotFoundException("Avaliação não encontrada."));

        var restaurante = restauranteRepository.findById(avaliacao.getRestaurante().getId());
        restaurante.get().getAvaliacoes().removeIf( x -> x.getRestaurante().getId().equals(avaliacao.getRestaurante().getId()));
        restaurante.get().getAvaliacoes().add(avaliacaoSaved);
        restauranteRepository.save(restaurante.get());

        var cliente = clienteRepository.findById(avaliacao.getCliente().getId());
        cliente.get().getAvaliacoes().removeIf( x -> x.getCliente().getId().equals(avaliacao.getCliente().getId()));
        cliente.get().getAvaliacoes().add(avaliacaoSaved);
        clienteRepository.save(cliente.get());

        return avaliacaoSaved;
    }

    public void excluir(String id) {
        Avaliacao avaliacao = avaliacaoRepository.findById(id)
                    .orElseThrow(() -> new ControllerNotFoundException("Avaliação não encontrada"));

        var restaurante = restauranteRepository.findById(avaliacao.getRestaurante().getId());
        restaurante.get().getAvaliacoes().removeIf( x -> x.getRestaurante().getId().equals(avaliacao.getRestaurante().getId()));
        restauranteRepository.save(restaurante.get());

        var cliente = clienteRepository.findById(avaliacao.getCliente().getId());
        cliente.get().getAvaliacoes().removeIf(x -> x.getCliente().getId().equals(avaliacao.getCliente().getId()));
        clienteRepository.save(cliente.get());

        avaliacaoRepository.deleteById(id);
    }

    public Avaliacao buscarPorId(String id) {
        return avaliacaoRepository.findById(id)
                .orElseThrow(() -> new ControllerNotFoundException("Avaliação não encontrada."));
    }

    public List<Avaliacao> buscarTodas() {
        return avaliacaoRepository.findAll();
    }
}
