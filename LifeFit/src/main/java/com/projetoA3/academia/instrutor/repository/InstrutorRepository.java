package com.projetoA3.academia.instrutor.repository;

import com.projetoA3.academia.instrutor.entity.Instrutor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstrutorRepository extends JpaRepository<Instrutor, Long> {
    boolean existsByEmail(String email);
}
