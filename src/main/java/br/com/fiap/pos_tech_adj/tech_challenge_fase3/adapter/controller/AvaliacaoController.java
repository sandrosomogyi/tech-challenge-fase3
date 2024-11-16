package br.com.fiap.pos_tech_adj.tech_challenge_fase3.adapter.controller;

import br.com.fiap.pos_tech_adj.tech_challenge_fase3.domain.Avaliacao;
import br.com.fiap.pos_tech_adj.tech_challenge_fase3.usecase.AdicionarAvaliacao;
import br.com.fiap.pos_tech_adj.tech_challenge_fase3.usecase.GerenciarAvaliacao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/avaliacoes")
public class AvaliacaoController {

    private final AdicionarAvaliacao adicionarAvaliacao;
    private final GerenciarAvaliacao gerenciarAvaliacao;

    @Autowired
    public AvaliacaoController(AdicionarAvaliacao adicionarAvaliacao, GerenciarAvaliacao gerenciarAvaliacao) {
        this.adicionarAvaliacao = adicionarAvaliacao;
        this.gerenciarAvaliacao = gerenciarAvaliacao;
    }

    @PostMapping
    public ResponseEntity<Avaliacao> adicionarAvaliacao(@RequestBody Avaliacao avaliacao) {
        Avaliacao avaliacaoCriada = adicionarAvaliacao.execute(avaliacao);
        return new ResponseEntity<>(avaliacaoCriada, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Avaliacao> atualizar(@PathVariable String id, @RequestBody Avaliacao avaliacao) {
        return ResponseEntity.ok(gerenciarAvaliacao.atualizar(id, avaliacao));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable String id) {
        gerenciarAvaliacao.excluir(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Avaliacao> buscarPorId(@PathVariable String id) {
        return ResponseEntity.ok(gerenciarAvaliacao.buscarPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<Avaliacao>> buscarTodas() {
        return ResponseEntity.ok(gerenciarAvaliacao.buscarTodas());
    }
}
