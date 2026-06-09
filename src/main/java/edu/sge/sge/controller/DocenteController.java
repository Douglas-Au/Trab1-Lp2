package edu.sge.sge.controller;

import edu.sge.sge.models.Docente;
import edu.sge.sge.services.DocenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/docentes")
public class DocenteController {

    @Autowired
    private DocenteService docenteService;

    @GetMapping
    public List<Docente> getAll() {
        return docenteService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Docente> getById(@PathVariable Long id) {
        return docenteService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/siape/{siape}")
    public ResponseEntity<Docente> getBySiape(@PathVariable String siape) {
        return docenteService.getBySiape(siape)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/departamento/{departamento}")
    public List<Docente> getByDepartamento(@PathVariable String departamento) {
        return docenteService.getByDepartamento(departamento);
    }

    @PostMapping
    public Docente create(@RequestBody Docente docente) {
        return docenteService.create(docente);
    }

    @PutMapping("/{id}")
    public Docente update(@PathVariable Long id, @RequestBody Docente docente) {
        return docenteService.update(id, docente);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        docenteService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
