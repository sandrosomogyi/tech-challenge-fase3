package br.com.fiap.pos_tech_adj.tech_challenge_fase3.adapter.controller;

import br.com.fiap.pos_tech_adj.tech_challenge_fase3.domain.entity.Avaliacao;
import br.com.fiap.pos_tech_adj.tech_challenge_fase3.usecase.AdicionarAvaliacao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/avaliacoes")
public class AvaliacaoController {

    private final AdicionarAvaliacao adicionarAvaliacao;

    @Autowired
    public AvaliacaoController(AdicionarAvaliacao adicionarAvaliacao) {
        this.adicionarAvaliacao = adicionarAvaliacao;
    }

    @PostMapping
    public ResponseEntity<Avaliacao> adicionarAvaliacao(@RequestBody Avaliacao avaliacao) {
        Avaliacao avaliacaoCriada = adicionarAvaliacao.execute(avaliacao);
        return new ResponseEntity<>(avaliacaoCriada, HttpStatus.CREATED);
    }
}
