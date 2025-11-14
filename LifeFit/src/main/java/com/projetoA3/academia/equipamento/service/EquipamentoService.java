package com.projetoA3.academia.equipamento.service;

import com.projetoA3.academia.equipamento.entity.Equipamento;
import com.projetoA3.academia.equipamento.GrupoMuscular;
import com.projetoA3.academia.equipamento.repository.EquipamentoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EquipamentoService{

    private final EquipamentoRepository repository;

    public EquipamentoService(EquipamentoRepository repository) {
        this.repository = repository;
    }


    @Transactional
    public Equipamento salvar(Equipamento equipamento) {
        if (equipamento.getQuantidade() < 0) {
            throw new IllegalArgumentException("Quantidade não pode ser negativa");
        }
        return repository.save(equipamento);
    }


    @Transactional(readOnly = true)
    public List<Equipamento> listarTodos() {
        return repository.findAll();
    }


    @Transactional(readOnly = true)
    public List<Equipamento> listarPorGrupo(GrupoMuscular grupo) {
        return repository.findByGrupoMuscular(grupo);
    }


    @Transactional
    public void remover(Long id) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("Equipamento não encontrado");
        }
        repository.deleteById(id);
    }
}
