package com.projetoA3.academia.aluno.controller;

import com.projetoA3.academia.aluno.entity.Aluno;
import com.projetoA3.academia.aluno.enums.StatusAluno;

import com.projetoA3.academia.aluno.service.AlunoService;
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
