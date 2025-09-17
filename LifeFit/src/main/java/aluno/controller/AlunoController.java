package aluno.controller;

import aluno.service.AlunoService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/alunos")
public class AlunoController {
    private final AlunoService alunoServices;

    public AlunoController(AlunoService alunoService) {
       this.alunoServices = alunoService;
   }

}
