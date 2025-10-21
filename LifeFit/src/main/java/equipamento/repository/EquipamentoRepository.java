package com.lifefit.academia.repository;

import com.lifefit.academia.model.Equipamento;
import com.lifefit.academia.model.GrupoMuscular;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EquipamentoRepository extends JpaRepository<Equipamento, Long> {
    List<Equipamento> findByGrupoMuscular(GrupoMuscular grupo);
}
