package br.com.fiap.pos_tech_adj.tech_challenge_fase3.adapter.controller;

import br.com.fiap.pos_tech_adj.tech_challenge_fase3.domain.entity.Restaurante;
import br.com.fiap.pos_tech_adj.tech_challenge_fase3.usecase.GerenciarRestaurante;
import br.com.fiap.pos_tech_adj.tech_challenge_fase3.usecase.CadastrarRestaurante;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restaurantes")
public class RestauranteController {

    private final CadastrarRestaurante cadastrarRestaurante;
    private final GerenciarRestaurante gerenciarRestaurante;

    @Autowired
    public RestauranteController(CadastrarRestaurante cadastrarRestaurante, GerenciarRestaurante gerenciarRestaurante) {
        this.cadastrarRestaurante = cadastrarRestaurante;
        this.gerenciarRestaurante = gerenciarRestaurante;
    }

    @PostMapping
    public ResponseEntity<Restaurante> cadastrarRestaurante(@RequestBody Restaurante restaurante) {
        Restaurante restauranteCadastrado = cadastrarRestaurante.execute(restaurante);
        return new ResponseEntity<>(restauranteCadastrado, HttpStatus.CREATED);
    }

    // Endpoint adicional para listar todos os restaurantes
    @GetMapping
    public ResponseEntity<List<Restaurante>> listarRestaurantes() {
        List<Restaurante> restaurantes = gerenciarRestaurante.buscarTodos();
        return ResponseEntity.ok(restaurantes);
    }

    // Endpoint adicional para listar todos os restaurantes por Tipo de Cozinha
    @GetMapping("/{tipoCozinha}")
    public ResponseEntity<List<Restaurante>> listarRestaurantesByTipoCozinha(@PathVariable String tipoCozinha) {
        List<Restaurante> restaurantes = gerenciarRestaurante.buscarTipoCozinha(tipoCozinha);
        return ResponseEntity.ok(restaurantes);
    }

    // Endpoint adicional para buscar restaurantes por ID
    @GetMapping("/{id}")
    public ResponseEntity<Restaurante> buscarRestaurantesByID(@PathVariable String id) {
        Restaurante restaurantes = gerenciarRestaurante.buscarPorId(id);
        return ResponseEntity.ok(restaurantes);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Restaurante> atualizar(@PathVariable String id, @RequestBody Restaurante restaurante) {
        return ResponseEntity.ok(gerenciarRestaurante.atualizar(id, restaurante));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable String id) {
        gerenciarRestaurante.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
