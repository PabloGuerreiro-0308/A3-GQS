package com.projetoA3.academia.aluno.repository;

import com.projetoA3.academia.aluno.entity.Aluno;
import com.lifefit.academia.model.enums.StatusAluno;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlunoRepository extends JpaRepository<Aluno, Long> {
    List<Aluno> findByStatus(StatusAluno status);
    boolean existsByNumeroMatricula(String numeroMatricula);
    boolean existsByCpf(String cpf);
    boolean existsByEmail(String email);
}
