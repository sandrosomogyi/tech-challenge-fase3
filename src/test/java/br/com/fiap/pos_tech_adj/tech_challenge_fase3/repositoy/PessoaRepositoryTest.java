package br.com.fiap.pos_tech_adj.tech_challenge_fase3.repositoy;

import br.com.fiap.pos_tech_adj.tech_challenge_fase3.domain.entity.Pessoa;
import br.com.fiap.pos_tech_adj.tech_challenge_fase3.domain.repository.PessoaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)  // Adicionando o MockitoExtension para inicializar os mocks
class PessoaRepositoryTest {

    @Mock
    private PessoaRepository pessoaRepository; // Mock do repositório

    private Pessoa pessoa;

    @BeforeEach
    void setUp() {
        pessoa = new Pessoa();
        pessoa.setEmail("bruno@gmail.com");
        pessoa.setNome("brunotes");
        pessoa.setSobrenome("User");
        pessoa.setCpf("12345678909");
        pessoa.setTelefone("999999999");
        pessoa.setSenha("password");
    }

    @Test
    void deveSalvarPessoa() {
        when(pessoaRepository.save(pessoa)).thenReturn(pessoa);
        Pessoa savedPessoa = pessoaRepository.save(pessoa);

        assertNotNull(savedPessoa);
        assertEquals(pessoa.getCpf(), savedPessoa.getCpf());
        verify(pessoaRepository, times(1)).save(pessoa);
    }

    @Test
    void deveBuscarPessoaPorCpf() {
        // Mock do comportamento do repositório para o método findByCpf
        when(pessoaRepository.findByCpf("12345678941")).thenReturn(Optional.of(pessoa));

        Optional<Pessoa> foundPessoa = pessoaRepository.findByCpf("12345678941");

        assertTrue(foundPessoa.isPresent());
        assertEquals(pessoa.getCpf(), foundPessoa.get().getCpf());
        verify(pessoaRepository, times(1)).findByCpf("12345678941"); // Verifica se findByCpf foi chamado uma vez
    }

    @Test
    void deveAtualizarPessoa() {
        pessoa.setNome("Updated Name");
        when(pessoaRepository.save(pessoa)).thenReturn(pessoa);
        Pessoa updatedPessoa = pessoaRepository.save(pessoa);
        assertEquals("Updated Name", updatedPessoa.getNome());
        verify(pessoaRepository, times(1)).save(pessoa); // Verifica se save foi chamado uma vez
    }

    @Test
    void deveDeletarPessoa() {

        doNothing().when(pessoaRepository).deleteById(pessoa.getCpf());
        pessoaRepository.deleteById(pessoa.getCpf());
        verify(pessoaRepository, times(1)).deleteById(pessoa.getCpf()); // Verifica se deleteById foi chamado uma vez
    }
}
