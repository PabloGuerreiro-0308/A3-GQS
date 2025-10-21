package com.lifefit.academia.controller;

import com.lifefit.academia.model.Equipamento;
import com.lifefit.academia.model.GrupoMuscular;
import com.lifefit.academia.service.EquipamentoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/equipamentos")
public class EquipamentoController {

    private final EquipamentoService service;

    public EquipamentoController(EquipamentoService service) {
        this.service = service;
    }

    @GetMapping
    public List<Equipamento> listarTodos() {
        return service.listarTodos();
    }

    @GetMapping("/grupo/{grupo}")
    public List<Equipamento> listarPorGrupo(@PathVariable GrupoMuscular grupo) {
        return service.listarPorGrupo(grupo);
    }

    @PostMapping
    public ResponseEntity<?> criar(@RequestBody @Valid Equipamento equipamento) {
        try {
            Equipamento salvo = service.salvar(equipamento);
            return ResponseEntity.ok(salvo);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> remover(@PathVariable Long id) {
        try {
            service.remover(id);
            return ResponseEntity.ok("Equipamento removido");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
