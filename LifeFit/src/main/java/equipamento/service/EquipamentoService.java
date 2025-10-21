package com.lifefit.academia.service;

import com.lifefit.academia.model.Equipamento;
import com.lifefit.academia.model.GrupoMuscular;

import java.util.List;

public interface EquipamentoService {
    Equipamento salvar(Equipamento equipamento);
    List<Equipamento> listarTodos();
    List<Equipamento> listarPorGrupo(GrupoMuscular grupo);
    void remover(Long id);
}
