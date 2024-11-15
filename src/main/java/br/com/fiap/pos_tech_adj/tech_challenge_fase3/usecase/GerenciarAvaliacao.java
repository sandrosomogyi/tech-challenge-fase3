package br.com.fiap.pos_tech_adj.tech_challenge_fase3.usecase;

import br.com.fiap.pos_tech_adj.tech_challenge_fase3.domain.entity.Avaliacao;
import br.com.fiap.pos_tech_adj.tech_challenge_fase3.domain.repository.AvaliacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GerenciarAvaliacao {

    private final AvaliacaoRepository avaliacaoRepository;

    @Autowired
    public GerenciarAvaliacao(AvaliacaoRepository avaliacaoRepository) {
        this.avaliacaoRepository = avaliacaoRepository;
    }

    public Avaliacao atualizar(String id, Avaliacao avaliacao) {
        return avaliacaoRepository.findById(id)
                .map(a -> {
                    a.setNota(avaliacao.getNota());
                    a.setComentario(avaliacao.getComentario());
                    return avaliacaoRepository.save(a);
                })
                .orElseThrow(() -> new IllegalArgumentException("Avaliação não encontrada."));
    }

    public void excluir(String id) {
        Avaliacao avaliacao = avaliacaoRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Avaliação não encontrada"));

        avaliacaoRepository.deleteById(id);
    }

    public Avaliacao buscarPorId(String id) {
        return avaliacaoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Avaliação não encontrada."));
    }

    public List<Avaliacao> buscarTodas() {
        return avaliacaoRepository.findAll();
    }
}
