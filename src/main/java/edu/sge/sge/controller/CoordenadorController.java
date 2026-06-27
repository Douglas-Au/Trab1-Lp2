package edu.sge.sge.controller;

import edu.sge.sge.models.CoordenadorUCE;
import edu.sge.sge.services.CoordenadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/coordenadores")
public class CoordenadorController {

    @Autowired
    private CoordenadorService coordenadorService;

    @GetMapping
    public List<CoordenadorUCE> getAll() {
        return coordenadorService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CoordenadorUCE> getById(@PathVariable Long id) {
        return coordenadorService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody CoordenadorUCE coordenador) {
        try {
            return ResponseEntity.ok(coordenadorService.create(coordenador));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public CoordenadorUCE update(@PathVariable Long id, @RequestBody CoordenadorUCE coordenador) {
        return coordenadorService.update(id, coordenador);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        coordenadorService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
