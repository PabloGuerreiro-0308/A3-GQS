package com.projetoA3.academia.fichaTreino.repository;

import com.projetoA3.academia.fichaTreino.entity.FichaTreino;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FichaTreinoRepository extends JpaRepository<FichaTreino, Long> {
}