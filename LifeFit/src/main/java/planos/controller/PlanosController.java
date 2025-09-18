package planos.controller;

import com.seuprojeto.academia.entity.Planos;
import com.seuprojeto.academia.service.PlanosService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/planos")
public class PlanosController {

    private final PlanosService planosService;

    public PlanosController(PlanosService planosService) {
        this.planosService = planosService;
    }

    @GetMapping
    public List<Planos> getAllPlanos() {
        return planosService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Planos> getPlanoById(@PathVariable Long id) {
        return planosService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Planos createPlano(@RequestBody Planos plano) {
        return planosService.save(plano);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Planos> updatePlano(@PathVariable Long id, @RequestBody Planos planoAtualizado) {
        return planosService.findById(id).map(plano -> {
            plano.setNome(planoAtualizado.getNome());
            plano.setDescricao(planoAtualizado.getDescricao());
            plano.setValor(planoAtualizado.getValor());
            plano.setDuracaoMeses(planoAtualizado.getDuracaoMeses());
            return ResponseEntity.ok(planosService.save(plano));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlano(@PathVariable Long id) {
        planosService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
