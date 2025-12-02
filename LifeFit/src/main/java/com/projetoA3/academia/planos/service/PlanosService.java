package com.projetoA3.academia.planos.service;

import com.projetoA3.academia.planos.entity.Planos;
import com.projetoA3.academia.planos.repository.PlanosRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlanosService {

    private final PlanosRepository planosRepository;

    public PlanosService(PlanosRepository planosRepository) {
        this.planosRepository = planosRepository;
    }

    public List<Planos> findAll() {
        return planosRepository.findAll();
    }

    public Optional<Planos> findById(Long id) {
        return planosRepository.findById(id);
    }

    public Planos save(Planos plano) {
        return planosRepository.save(plano);
    }

    public void deleteById(Long id) {
        planosRepository.deleteById(id);
    }
}
