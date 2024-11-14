package br.com.fiap.pos_tech_adj.tech_challenge_fase3.adapter.controller;

import br.com.fiap.pos_tech_adj.tech_challenge_fase3.domain.entity.Restaurante;
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

    @Autowired
    public RestauranteController(CadastrarRestaurante cadastrarRestaurante) {
        this.cadastrarRestaurante = cadastrarRestaurante;
    }

    @PostMapping
    public ResponseEntity<Restaurante> cadastrarRestaurante(@RequestBody Restaurante restaurante) {
        Restaurante restauranteCadastrado = cadastrarRestaurante.execute(restaurante);
        return new ResponseEntity<>(restauranteCadastrado, HttpStatus.CREATED);
    }

    // Endpoint adicional para listar todos os restaurantes (exemplo)
    @GetMapping
    public ResponseEntity<List<Restaurante>> listarRestaurantes() {
        List<Restaurante> restaurantes = cadastrarRestaurante.buscarTodos();
        return ResponseEntity.ok(restaurantes);
    }
}
