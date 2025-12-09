package com.projetoA3.academia.instrutor.service;

import com.projetoA3.academia.instrutor.entity.Instrutor;
import com.projetoA3.academia.instrutor.repository.InstrutorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class InstrutorServiceImplTest {

    @Mock
    private InstrutorRepository instrutorRepository;

    @InjectMocks
    private InstrutorServiceImpl instrutorService;

    private Instrutor instrutor;

    @BeforeEach
    void setUp() {
        instrutor = new Instrutor(1L, "Maria Santos", 25, "maria.santos@academia.com", "11888888888", "Yoga", Arrays.asList());
    }

    // --- Testes para salvar ---
    @Test
    void shouldSaveInstrutorSuccessfully() {
        // Cenário
        when(instrutorRepository.existsByEmail(instrutor.getEmail())).thenReturn(false);
        when(instrutorRepository.save(instrutor)).thenReturn(instrutor);

        // Ação
        Instrutor salvo = instrutorService.salvar(instrutor);

        // Verificação
        assertNotNull(salvo);
        assertEquals(instrutor.getNome(), salvo.getNome());
        verify(instrutorRepository, times(1)).existsByEmail(instrutor.getEmail());
        verify(instrutorRepository, times(1)).save(instrutor);
    }

    @Test
    void shouldThrowExceptionWhenEmailAlreadyExistsOnSave() {
        // Cenário
        when(instrutorRepository.existsByEmail(instrutor.getEmail())).thenReturn(true);

        // Ação e Verificação
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            instrutorService.salvar(instrutor);
        });

        assertEquals("Email já cadastrado", exception.getMessage());
        verify(instrutorRepository, times(1)).existsByEmail(instrutor.getEmail());
        verify(instrutorRepository, never()).save(any(Instrutor.class));
    }

    // --- Testes para listarTodos ---
    @Test
    void shouldReturnAllInstrutores() {
        // Cenário
        Instrutor instrutor2 = new Instrutor(2L, "Pedro", 35, "pedro@academia.com", "11777777777", "Musculação", Arrays.asList());
        List<Instrutor> listaEsperada = Arrays.asList(instrutor, instrutor2);
        when(instrutorRepository.findAll()).thenReturn(listaEsperada);

        // Ação
        List<Instrutor> resultado = instrutorService.listarTodos();

        // Verificação
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(instrutorRepository, times(1)).findAll();
    }

    // --- Testes para remover ---
    @Test
    void shouldRemoveInstrutorSuccessfully() {
        // Cenário
        Long id = 1L;
        when(instrutorRepository.existsById(id)).thenReturn(true);
        doNothing().when(instrutorRepository).deleteById(id);

        // Ação
        instrutorService.remover(id);

        // Verificação
        verify(instrutorRepository, times(1)).existsById(id);
        verify(instrutorRepository, times(1)).deleteById(id);
    }

    @Test
    void shouldThrowExceptionWhenInstrutorNotFoundOnRemove() {
        // Cenário
        Long id = 99L;
        when(instrutorRepository.existsById(id)).thenReturn(false);

        // Ação e Verificação
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            instrutorService.remover(id);
        });

        assertEquals("Instrutor não encontrado", exception.getMessage());
        verify(instrutorRepository, times(1)).existsById(id);
        verify(instrutorRepository, never()).deleteById(anyLong());
    }

    // --- Testes para adicionarTurma ---
    @Test
    void shouldAddTurmaSuccessfully() {
        // Cenário
        Long id = 1L;
        String turma = "Pilates A";
        when(instrutorRepository.findById(id)).thenReturn(Optional.of(instrutor));
        when(instrutorRepository.save(instrutor)).thenReturn(instrutor);

        // Ação
        Instrutor atualizado = instrutorService.adicionarTurma(id, turma);

        // Verificação
        assertNotNull(atualizado);
        assertTrue(atualizado.getTurmas().contains(turma));
        verify(instrutorRepository, times(1)).findById(id);
        verify(instrutorRepository, times(1)).save(instrutor);
    }

    @Test
    void shouldThrowExceptionWhenInstrutorNotFoundOnAdicionarTurma() {
        // Cenário
        Long id = 99L;
        String turma = "Pilates A";
        when(instrutorRepository.findById(id)).thenReturn(Optional.empty());

        // Ação e Verificação
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            instrutorService.adicionarTurma(id, turma);
        });

        assertEquals("Instrutor não encontrado", exception.getMessage());
        verify(instrutorRepository, times(1)).findById(id);
        verify(instrutorRepository, never()).save(any(Instrutor.class));
    }
}