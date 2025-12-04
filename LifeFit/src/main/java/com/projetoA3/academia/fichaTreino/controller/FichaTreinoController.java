package com.projetoA3.academia.fichaTreino.controller;

import com.projetoA3.academia.fichaTreino.entity.FichaTreino;
import com.projetoA3.academia.fichaTreino.entity.ItemTreino;
import com.projetoA3.academia.fichaTreino.service.FichaTreinoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fichas-treino")
public class FichaTreinoController {

    private final FichaTreinoService fichaTreinoService;

    public FichaTreinoController(FichaTreinoService fichaTreinoService) {
        this.fichaTreinoService = fichaTreinoService;
    }

    @PostMapping
    public ResponseEntity<FichaTreino> create(@RequestBody @Valid FichaTreino fichaTreino) {
        FichaTreino novaFicha = fichaTreinoService.criarFicha(fichaTreino);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaFicha);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FichaTreino> findById(
            @PathVariable Long id,
            @RequestParam(required = false) String treino) {
        FichaTreino ficha = fichaTreinoService.buscarFichaPorId(id, treino);
        return ResponseEntity.ok(ficha);
    }

    @PostMapping("/{id}/progredir")
    public ResponseEntity<FichaTreino> progredirTreino(@PathVariable Long id) {
        FichaTreino fichaAtualizada = fichaTreinoService.obterEProgredirTreino(id);
        return ResponseEntity.ok(fichaAtualizada);
    }
    @GetMapping("/{id}/treino-atual")
    public ResponseEntity<List<ItemTreino>> getTreinoAtual(@PathVariable Long id) {
 
        List<ItemTreino> itensDoDia = fichaTreinoService.buscarProximoTreino(id);
        return ResponseEntity.ok(itensDoDia);
    }
}