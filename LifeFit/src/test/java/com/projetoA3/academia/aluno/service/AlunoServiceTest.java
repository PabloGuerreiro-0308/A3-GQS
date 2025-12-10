package com.projetoA3.aluno.service;

import com.projetoA3.aluno.model.Aluno;
import com.projetoA3.aluno.repository.AlunoRepository;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AlunoServiceTest {

    @Mock
    private AlunoRepository alunoRepository;

    @InjectMocks
    private AlunoService alunoService;

    private Aluno aluno;

    @BeforeEach
    void setUp() {
        aluno = new Aluno(
                1L,
                "João Silva",
                "joao@email.com",
                "(11) 98765-4321",
                25,
                "123.456.789-00",
                "01/12/2025",
                "Rua A, 123",
                "MAT001"
        );
    }

    @Test
    void testCriarAlunoComSucesso() {
        // Arrange
        when(alunoRepository.findByNumeroMatricula("MAT001")).thenReturn(Optional.empty());
        when(alunoRepository.findByCpf("123.456.789-00")).thenReturn(Optional.empty());
        when(alunoRepository.save(any(Aluno.class))).thenReturn(aluno);

        // Act
        Aluno resultado = alunoService.criarAluno(aluno);

        // Assert
        assertNotNull(resultado);
        assertEquals("João Silva", resultado.getNome());
        assertEquals("joao@email.com", resultado.getEmail());
        verify(alunoRepository, times(1)).save(aluno);
    }

    @Test
    void testCriarAlunoComMatriculaDuplicada() {
        // Arrange
        when(alunoRepository.findByNumeroMatricula("MAT001")).thenReturn(Optional.of(aluno));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> alunoService.criarAluno(aluno));
        verify(alunoRepository, never()).save(any());
    }

    @Test
    void testCriarAlunoComCpfDuplicado() {
        // Arrange
        when(alunoRepository.findByNumeroMatricula("MAT001")).thenReturn(Optional.empty());
        when(alunoRepository.findByCpf("123.456.789-00")).thenReturn(Optional.of(aluno));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> alunoService.criarAluno(aluno));
        verify(alunoRepository, never()).save(any());
    }

    @Test
    void testObterAlunoPorIdComSucesso() {
        // Arrange
        when(alunoRepository.findById(1L)).thenReturn(Optional.of(aluno));

        // Act
        Aluno resultado = alunoService.obterAlunoPorId(1L);

        // Assert
        assertNotNull(resultado);
        assertEquals("João Silva", resultado.getNome());
        verify(alunoRepository, times(1)).findById(1L);
    }

    @Test
    void testObterAlunoPorIdNaoEncontrado() {
        // Arrange
        when(alunoRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> alunoService.obterAlunoPorId(999L));
    }

    @Test
    void testObterAlunoPorMatriculaComSucesso() {
        // Arrange
        when(alunoRepository.findByNumeroMatricula("MAT001")).thenReturn(Optional.of(aluno));

        // Act
        Aluno resultado = alunoService.obterAlunoPorMatricula("MAT001");

        // Assert
        assertNotNull(resultado);
        assertEquals("MAT001", resultado.getNumeroMatricula());
        verify(alunoRepository, times(1)).findByNumeroMatricula("MAT001");
    }

    @Test
    void testObterTodosAlunos() {
        // Arrange
        Aluno aluno2 = new Aluno(2L, "Maria", "maria@email.com", "(11) 99999-9999", 23,
                "987.654.321-00", "01/12/2025", "Rua B, 456", "MAT002");
        List<Aluno> alunos = Arrays.asList(aluno, aluno2);

        when(alunoRepository.findAll()).thenReturn(alunos);

        // Act
        List<Aluno> resultado = alunoService.obterTodos();

        // Assert
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(alunoRepository, times(1)).findAll();
    }

    @Test
    void testBuscarAlunoPorNome() {
        // Arrange
        List<Aluno> alunos = Arrays.asList(aluno);
        when(alunoRepository.findByNomeContainingIgnoreCase("João")).thenReturn(alunos);

        // Act
        List<Aluno> resultado = alunoService.buscarPorNome("João");

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("João Silva", resultado.get(0).getNome());
        verify(alunoRepository, times(1)).findByNomeContainingIgnoreCase("João");
    }

    @Test
    void testAtualizarAlunoComSucesso() {
        // Arrange
        Aluno alunoAtualizado = new Aluno(1L, "João Silva Junior", "joao.junior@email.com",
                "(11) 98765-4321", 26, "123.456.789-00", "01/12/2026", "Rua C, 789", "MAT001");

        when(alunoRepository.findById(1L)).thenReturn(Optional.of(aluno));
        when(alunoRepository.save(any(Aluno.class))).thenReturn(alunoAtualizado);

        // Act
        Aluno resultado = alunoService.atualizarAluno(1L, alunoAtualizado);

        // Assert
        assertNotNull(resultado);
        assertEquals("João Silva Junior", resultado.getNome());
        verify(alunoRepository, times(1)).save(any(Aluno.class));
    }

    @Test
    void testDeletarAlunoComSucesso() {
        // Arrange
        when(alunoRepository.findById(1L)).thenReturn(Optional.of(aluno));
        doNothing().when(alunoRepository).delete(aluno);

        // Act
        alunoService.deletarAluno(1L);

        // Assert
        verify(alunoRepository, times(1)).delete(aluno);
    }

    @Test
    void testDeletarAlunoNaoEncontrado() {
        // Arrange
        when(alunoRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> alunoService.deletarAluno(999L));
    }
}
