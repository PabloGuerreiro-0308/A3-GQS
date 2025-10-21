package com.lifefit.academia.service;

import com.lifefit.academia.model.Aluno;
import com.lifefit.academia.model.enums.StatusAluno;
import com.lifefit.academia.repository.AlunoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class AlunoServiceImpl implements AlunoService {

    private final AlunoRepository repository;

    public AlunoServiceImpl(AlunoRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Aluno> listarTodos() {
        return repository.findAll();
    }

    @Override
    public List<Aluno> listarPorStatus(StatusAluno status) {
        return repository.findByStatus(status);
    }

    @Override
    @Transactional
    public Aluno salvar(Aluno aluno) {
        validarUnicidade(aluno);
        if (aluno.getDataVencimento() == null) {
            aluno.setDataVencimento(LocalDate.now().plusMonths(1));
        }
        if (aluno.getStatus() == null) {
            aluno.setStatus(StatusAluno.ATIVO);
        }
        return repository.save(aluno);
    }

    private void validarUnicidade(Aluno aluno) {
        if (aluno.getNumeroMatricula() != null && repository.existsByNumeroMatricula(aluno.getNumeroMatricula())) {
            throw new IllegalArgumentException("Número de matrícula já cadastrado");
        }
        if (aluno.getCpf() != null && repository.existsByCpf(aluno.getCpf())) {
            throw new IllegalArgumentException("CPF já cadastrado");
        }
        if (aluno.getEmail() != null && repository.existsByEmail(aluno.getEmail())) {
            throw new IllegalArgumentException("Email já cadastrado");
        }
    }
}
