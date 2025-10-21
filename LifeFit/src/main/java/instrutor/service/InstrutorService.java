package com.lifefit.academia.service;

import com.lifefit.academia.model.Instrutor;

import java.util.List;

public interface InstrutorService {
    Instrutor salvar(Instrutor instrutor);
    List<Instrutor> listarTodos();
    void remover(Long id);
    Instrutor adicionarTurma(Long id, String turma);
}
