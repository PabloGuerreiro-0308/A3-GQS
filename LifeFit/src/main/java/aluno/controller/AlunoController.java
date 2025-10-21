package com.lifefit.academia.controller;

import com.lifefit.academia.model.Aluno;
import com.lifefit.academia.model.enums.StatusAluno;
import com.lifefit.academia.service.AlunoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alunos")
public class AlunoController {

    private final AlunoService service;

    public AlunoController(AlunoService service) {
        this.service = service;
    }

    @GetMapping
    public List<Aluno> listarTodos() {
        return service.listarTodos();
    }

    @GetMapping("/status/{status}")
    public List<Aluno> listarPorStatus(@PathVariable StatusAluno status) {
        return service.listarPorStatus(status);
    }

    @PostMapping
    public ResponseEntity<?> salvar(@RequestBody Aluno aluno) {
        try {
            Aluno salvo = service.salvar(aluno);
            return ResponseEntity.ok(salvo);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
