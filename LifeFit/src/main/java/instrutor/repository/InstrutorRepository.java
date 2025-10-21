package com.lifefit.academia.repository;

import com.lifefit.academia.model.Instrutor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstrutorRepository extends JpaRepository<Instrutor, Long> {
    boolean existsByEmail(String email);
}
