package com.projetoA3.aluno.controller;

import com.projetoA3.aluno.model.Aluno;
import com.projetoA3.aluno.service.AlunoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AlunoControllerTest {

    @Mock
    private AlunoService alunoService;

    @InjectMocks
    private AlunoController alunoController;

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
    void testCriarAlunoEndpoint() {
        // Arrange
        when(alunoService.criarAluno(any(Aluno.class))).thenReturn(aluno);

        // Act
        ResponseEntity<Aluno> response = alunoController.criarAluno(aluno);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("João Silva", response.getBody().getNome());
        verify(alunoService, times(1)).criarAluno(any(Aluno.class));
    }

    @Test
    void testObterAlunoPorIdEndpoint() {
        // Arrange
        when(alunoService.obterAlunoPorId(1L)).thenReturn(aluno);

        // Act
        ResponseEntity<Aluno> response = alunoController.obterAlunoPorId(1L);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1L, response.getBody().getId());
        verify(alunoService, times(1)).obterAlunoPorId(1L);
    }

    @Test
    void testObterTodosEndpoint() {
        // Arrange
        List<Aluno> alunos = Arrays.asList(aluno);
        when(alunoService.obterTodos()).thenReturn(alunos);

        // Act
        ResponseEntity<List<Aluno>> response = alunoController.obterTodos();

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        verify(alunoService, times(1)).obterTodos();
    }
}
