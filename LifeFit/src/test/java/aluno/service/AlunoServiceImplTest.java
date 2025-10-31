package aluno.service;

import aluno.entity.Aluno;
import aluno.repository.AlunoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AlunoServiceImplTest {

    @Mock
    AlunoRepository repository;

    @Test
    void deveListarTodosOsAlunos(){
        AlunoServiceImpl alunoService = new AlunoServiceImpl(repository);

        Aluno aluno = new Aluno();
        aluno.setNome("Thiago");

        when(repository.findAll()).thenReturn(List.of(aluno));

        List<Aluno> alunos = alunoService.listarTodos();

        assertEquals("Thiago",alunos.get(0).getNome());
    }

}