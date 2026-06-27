package edu.sge.sge.controller;

import edu.sge.sge.models.DiscenteCursoHistorico;
import edu.sge.sge.services.DiscenteCursoHistoricoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/discentes-curso-historico")
public class DiscenteCursoHistoricoController {

    @Autowired
    private DiscenteCursoHistoricoService service;

    @GetMapping
    public List<DiscenteCursoHistorico> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DiscenteCursoHistorico> getById(@PathVariable Long id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/discente/{discenteId}")
    public List<DiscenteCursoHistorico> getByDiscente(@PathVariable Long discenteId) {
        return service.getByDiscente(discenteId);
    }

    @PostMapping
    public DiscenteCursoHistorico create(@RequestBody DiscenteCursoHistorico historico) {
        return service.create(historico);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
