package com.projetoA3.academia.fichaTreino.repository;

import com.projetoA3.academia.fichaTreino.entity.ItemTreino;
import com.projetoA3.academia.fichaTreino.tipoTreino.TipoTreino;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemTreinoRepository extends JpaRepository<ItemTreino, Long> {


    List<ItemTreino> findByFichaTreino_IdOrderByOrdemAsc(Long fichaId);

    List<ItemTreino> findByFichaTreino_IdAndTipoTreinoOrderByOrdemAsc(Long fichaId, TipoTreino tipoTreino);
}