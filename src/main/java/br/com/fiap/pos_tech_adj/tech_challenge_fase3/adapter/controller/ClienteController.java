package br.com.fiap.pos_tech_adj.tech_challenge_fase3.adapter.controller;

import br.com.fiap.pos_tech_adj.tech_challenge_fase3.domain.entity.Cliente;
import br.com.fiap.pos_tech_adj.tech_challenge_fase3.usecase.CadastrarCliente;
import br.com.fiap.pos_tech_adj.tech_challenge_fase3.usecase.GerenciarCliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private final CadastrarCliente cadastrarCliente;
    private final GerenciarCliente gerenciarCliente;

    @Autowired
    public ClienteController(CadastrarCliente cadastrarCliente, GerenciarCliente gerenciarCliente) {
        this.cadastrarCliente = cadastrarCliente;
        this.gerenciarCliente = gerenciarCliente;
    }

    @PostMapping("/{pessoaId}")
    public ResponseEntity<Cliente> cadastrar(@PathVariable String pessoaId) {
        Cliente cliente = cadastrarCliente.execute(pessoaId);
        return ResponseEntity.status(201).body(cliente);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cliente> atualizar(@PathVariable String id, @RequestBody Cliente cliente) {
        return ResponseEntity.ok(gerenciarCliente.atualizar(id, cliente));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable String id) {
        gerenciarCliente.excluir(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> buscarPorId(@PathVariable String id) {
        return ResponseEntity.ok(gerenciarCliente.buscarPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<Cliente>> buscarTodos() {
        return ResponseEntity.ok(gerenciarCliente.buscarTodos());
    }
}
