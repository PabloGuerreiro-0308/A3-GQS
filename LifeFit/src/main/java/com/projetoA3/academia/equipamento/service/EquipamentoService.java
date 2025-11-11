package com.projetoA3.academia.equipamento.service;

import com.projetoA3.academia.equipamento.entify.Equipamento;
import com.projetoA3.academia.equipamento.GrupoMuscular;

import java.util.List;

public interface EquipamentoService {
    Equipamento salvar(Equipamento equipamento);
    List<Equipamento> listarTodos();
    List<Equipamento> listarPorGrupo(GrupoMuscular grupo);
    void remover(Long id);
}
