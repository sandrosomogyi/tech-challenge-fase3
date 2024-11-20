package br.com.fiap.pos_tech_adj.tech_challenge_fase3.domain.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.springframework.data.mongodb.core.MongoTemplate;

@DataMongoTest
@ExtendWith(SpringExtension.class)
@ComponentScan(basePackages = "br.com.fiap.pos_tech_adj")

public class PessoaTest {

    @Autowired
    private MongoTemplate mongoTemplate;

    private Pessoa pessoa1;

    @BeforeEach
    public void setUp() {
        pessoa1 = new Pessoa();
        pessoa1.setEmail("pessoa2@example.com");
        pessoa1.setNome("Bruno");
        pessoa1.setSobrenome("Silva");
        pessoa1.setCpf("12345678900");
        pessoa1.setTelefone("11987654321");
        pessoa1.setSenha("senha123");
    }
    @Test
    void testGettersSetters() {
        // Verificando se os valores definidos com os setters são retornados corretamente pelos getters
        assertEquals("pessoa2@example.com", pessoa1.getEmail());
        assertEquals("Bruno", pessoa1.getNome());
        assertEquals("Silva", pessoa1.getSobrenome());
        assertEquals("12345678900", pessoa1.getCpf());
        assertEquals("11987654321", pessoa1.getTelefone());
        assertEquals("senha123", pessoa1.getSenha());
    }


}
