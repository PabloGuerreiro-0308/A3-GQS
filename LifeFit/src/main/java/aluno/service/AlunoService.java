package aluno.service;

import aluno.entity.Aluno;
import aluno.StatusAluno;
import aluno.repository.AlunoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

public interface AlunoService {

    List<Aluno> listarTodos();

    Aluno salvar(Aluno aluno);

    List<Aluno> listarPorStatus(StatusAluno status);
    
}
