package com.projetoA3.academia.instrutor.service;

import com.projetoA3.academia.instrutor.entity.Instrutor;

import java.util.List;

public interface InstrutorService {
    Instrutor salvar(Instrutor instrutor);
    List<Instrutor> listarTodos();
    void remover(Long id);
    Instrutor adicionarTurma(Long id, String turma);
}
