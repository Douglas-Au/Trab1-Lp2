package edu.sge.sge.controller;

import edu.sge.sge.models.Discente;
import edu.sge.sge.services.DiscenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/discentes")
public class DiscenteController {

    @Autowired
    private DiscenteService discenteService;

    @GetMapping
    public List<Discente> getAll() {
        return discenteService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Discente> getById(@PathVariable Long id) {
        return discenteService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/matricula/{matricula}")
    public ResponseEntity<Discente> getByMatricula(@PathVariable String matricula) {
        return discenteService.getByMatricula(matricula)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Discente create(@RequestBody Discente discente) {
        return discenteService.create(discente);
    }

    @PutMapping("/{id}")
    public Discente update(@PathVariable Long id, @RequestBody Discente discente) {
        return discenteService.update(id, discente);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        discenteService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
