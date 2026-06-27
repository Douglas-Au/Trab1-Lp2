package edu.sge.sge.controller;

import edu.sge.sge.models.Comunicado;
import edu.sge.sge.services.ComunicadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comunicados")
public class ComunicadoController {

    @Autowired
    private ComunicadoService comunicadoService;

    @GetMapping
    public List<Comunicado> getAll() {
        return comunicadoService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Comunicado> getById(@PathVariable Long id) {
        return comunicadoService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/curso/{cursoId}")
    public List<Comunicado> getByCurso(@PathVariable Long cursoId) {
        return comunicadoService.getByCurso(cursoId);
    }

    @PostMapping
    public Comunicado create(@RequestBody Comunicado comunicado) {
        return comunicadoService.create(comunicado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        comunicadoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
