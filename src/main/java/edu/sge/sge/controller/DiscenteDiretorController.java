package edu.sge.sge.controller;

import edu.sge.sge.models.DiscenteDiretor;
import edu.sge.sge.services.DiscenteDiretorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/discentes-diretores")
public class DiscenteDiretorController {

    @Autowired
    private DiscenteDiretorService service;

    @GetMapping
    public List<DiscenteDiretor> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DiscenteDiretor> getById(@PathVariable Long id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/grupo/{grupoId}")
    public List<DiscenteDiretor> getByGrupo(@PathVariable Long grupoId) {
        return service.getByGrupo(grupoId);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody DiscenteDiretor diretor) {
        try {
            return ResponseEntity.ok(service.create(diretor));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public DiscenteDiretor update(@PathVariable Long id, @RequestBody DiscenteDiretor diretor) {
        return service.update(id, diretor);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
