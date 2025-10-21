package aluno.repository;

import aluno.entity.Aluno;
import aluno.StatusAluno;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlunoRepository extends JpaRepository<Aluno, Long> {
    List<Aluno> findByStatus(StatusAluno status);
    boolean existsByNumeroMatricula(String numeroMatricula);
    boolean existsByCpf(String cpf);
    boolean existsByEmail(String email);
}
