package com.projetoA3.academia.planos.repository;

import com.projetoA3.academia.planos.entity.Planos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanosRepository extends JpaRepository<Planos, Long> {
}
