package br.com.fiap.pos_tech_adj.tech_challenge_fase3.usecase;

import br.com.fiap.pos_tech_adj.tech_challenge_fase3.domain.entity.Administrador;
import br.com.fiap.pos_tech_adj.tech_challenge_fase3.domain.repository.AdministradorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GerenciarAdministrador {

    private final AdministradorRepository administradorRepository;

    @Autowired
    public GerenciarAdministrador(AdministradorRepository administradorRepository) {
        this.administradorRepository = administradorRepository;
    }

    public Administrador atualizar(String id, Administrador administrador) {
        return administradorRepository.findById(id)
                .map(existingAdmin -> {
                    existingAdmin.setPessoa(administrador.getPessoa());
                    existingAdmin.setRestaurantes(administrador.getRestaurantes());
                    return administradorRepository.save(existingAdmin);
                })
                .orElseThrow(() -> new IllegalArgumentException("Administrador não encontrado."));
    }

    public void excluir(String id) {
        Administrador administrador = administradorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Administrador não encontrado."));

        administradorRepository.deleteById(id);
    }

    public Administrador buscarPorId(String id) {
        return administradorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Administrador não encontrado."));
    }

    public List<Administrador> buscarTodos() {
        return administradorRepository.findAll();
    }
}
