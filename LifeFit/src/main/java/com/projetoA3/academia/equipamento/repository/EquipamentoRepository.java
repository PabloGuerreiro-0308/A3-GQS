package com.projetoA3.academia.equipamento.repository;

import com.projetoA3.academia.equipamento.entity.Equipamento;
import com.projetoA3.academia.equipamento.GrupoMuscular;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EquipamentoRepository extends JpaRepository<Equipamento, Long> {
    List<Equipamento> findByGrupoMuscular(GrupoMuscular grupo);
}
