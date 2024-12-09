package br.com.fiap.pos_tech_adj.tech_challenge_fase3.repositoy;

import br.com.fiap.pos_tech_adj.tech_challenge_fase3.domain.entity.Administrador;
import br.com.fiap.pos_tech_adj.tech_challenge_fase3.domain.entity.Pessoa;
import br.com.fiap.pos_tech_adj.tech_challenge_fase3.domain.entity.Restaurante;
import br.com.fiap.pos_tech_adj.tech_challenge_fase3.domain.repository.AdministradorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Optional;

class AdministradorRepositoryTest {

    @Mock
    private AdministradorRepository administradorRepository;

    @InjectMocks  // Isso vai garantir que o mock será injetado aqui
    private Administrador administrador;

    private Restaurante restaurante;
    private Pessoa pessoa;

    @BeforeEach
    void setUp() {
        // Inicializa os mocks manualmente se não estiver usando @ExtendWith(MockitoExtension.class)
        MockitoAnnotations.openMocks(this);

        pessoa = new Pessoa("pessoa@example.com", "João", "Silva", "98765432100", "11987654321", "senha123", "Rua ABC", 1L);
        restaurante = new Restaurante("Restaurante A", "123456789", "Endereço A", "Restaurante A", 10); // Passando 10 como 'int'

        administrador = new Administrador();
        administrador.setId("1");
        administrador.setPessoa(pessoa);
        administrador.setRestaurantes(Arrays.asList(restaurante));
        administrador.setVersion(1L);
    }

    @Test
    void deveSalvarAdministrador() {
        when(administradorRepository.save(administrador)).thenReturn(administrador);

        Administrador savedAdministrador = administradorRepository.save(administrador);

        assertNotNull(savedAdministrador);
        assertEquals("1", savedAdministrador.getId());
        assertEquals(pessoa.getEmail(), savedAdministrador.getPessoa().getEmail());
        assertEquals(1, savedAdministrador.getRestaurantes().size());
        verify(administradorRepository, times(1)).save(administrador);
    }

    @Test
    void deveBuscarAdministradorPorId() {
        when(administradorRepository.findById("1")).thenReturn(Optional.of(administrador));

        Optional<Administrador> foundAdministrador = administradorRepository.findById("1");

        assertTrue(foundAdministrador.isPresent());
        assertEquals("1", foundAdministrador.get().getId());
        assertEquals(pessoa.getEmail(), foundAdministrador.get().getPessoa().getEmail());
        verify(administradorRepository, times(1)).findById("1");
    }

    @Test
    void deveBuscarAdministradorPorPessoaId() {
        when(administradorRepository.findByPessoaId(pessoa.getId())).thenReturn(Optional.of(administrador));

        Optional<Administrador> foundAdministrador = administradorRepository.findByPessoaId(pessoa.getId());

        assertTrue(foundAdministrador.isPresent());
        assertEquals(pessoa.getId(), foundAdministrador.get().getPessoa().getId());
        verify(administradorRepository, times(1)).findByPessoaId(pessoa.getId());
    }

    @Test
    void deveDeletarAdministrador() {
        doNothing().when(administradorRepository).deleteById("1");

        administradorRepository.deleteById("1");

        verify(administradorRepository, times(1)).deleteById("1");
    }
}
