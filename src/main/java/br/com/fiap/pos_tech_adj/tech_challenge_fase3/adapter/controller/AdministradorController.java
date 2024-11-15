package br.com.fiap.pos_tech_adj.tech_challenge_fase3.adapter.controller;

import br.com.fiap.pos_tech_adj.tech_challenge_fase3.domain.entity.Administrador;
import br.com.fiap.pos_tech_adj.tech_challenge_fase3.usecase.CadastrarAdministrador;
import br.com.fiap.pos_tech_adj.tech_challenge_fase3.usecase.GerenciarAdministrador;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/administradores")
public class AdministradorController {

    private final CadastrarAdministrador cadastrarAdministrador;
    private final GerenciarAdministrador gerenciarAdministrador;

    public AdministradorController(CadastrarAdministrador cadastrarAdministrador, GerenciarAdministrador gerenciarAdministrador) {
        this.cadastrarAdministrador = cadastrarAdministrador;
        this.gerenciarAdministrador = gerenciarAdministrador;
    }

    @PostMapping("/{pessoaId}")
    public ResponseEntity<Administrador> cadastrar(@PathVariable String pessoaId) {
        Administrador administrador = cadastrarAdministrador.execute(pessoaId);
        return ResponseEntity.status(201).body(administrador);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Administrador> atualizar(@PathVariable String id, @RequestBody Administrador administrador) {
        return ResponseEntity.ok(gerenciarAdministrador.atualizar(id, administrador));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable String id) {
        gerenciarAdministrador.excluir(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Administrador> buscarPorId(@PathVariable String id) {
        return ResponseEntity.ok(gerenciarAdministrador.buscarPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<Administrador>> buscarTodos() {
        return ResponseEntity.ok(gerenciarAdministrador.buscarTodos());
    }
}
