package br.com.fiap.pos_tech_adj.tech_challenge_fase3.adapter.controller;

import br.com.fiap.pos_tech_adj.tech_challenge_fase3.domain.Pessoa;
import br.com.fiap.pos_tech_adj.tech_challenge_fase3.usecase.CadastrarPessoa;
import br.com.fiap.pos_tech_adj.tech_challenge_fase3.usecase.GerenciarPessoa;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pessoas")
public class PessoaController {

    private final CadastrarPessoa cadastrarPessoa;
    private final GerenciarPessoa gerenciarPessoa;

    public PessoaController(CadastrarPessoa cadastrarPessoa, GerenciarPessoa gerenciarPessoa) {
        this.cadastrarPessoa = cadastrarPessoa;
        this.gerenciarPessoa = gerenciarPessoa;
    }

    @PostMapping
    public ResponseEntity<Pessoa> cadastrar(@RequestBody Pessoa pessoa) {
        Pessoa novaPessoa = cadastrarPessoa.execute(pessoa);
        return ResponseEntity.status(201).body(novaPessoa);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pessoa> atualizar(@PathVariable String id, @RequestBody Pessoa pessoa) {
        return ResponseEntity.ok(gerenciarPessoa.atualizar(id, pessoa));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable String id) {
        gerenciarPessoa.excluir(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pessoa> buscarPorId(@PathVariable String id) {
        return ResponseEntity.ok(gerenciarPessoa.buscarPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<Pessoa>> buscarTodos() {
        return ResponseEntity.ok(gerenciarPessoa.buscarTodos());
    }
}
