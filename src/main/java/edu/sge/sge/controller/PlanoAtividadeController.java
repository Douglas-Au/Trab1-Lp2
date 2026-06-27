package edu.sge.sge.controller;

import edu.sge.sge.models.PlanoAtividade;
import edu.sge.sge.services.PlanoAtividadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/planos-atividade")
public class PlanoAtividadeController {

    @Autowired
    private PlanoAtividadeService planoAtividadeService;

    @GetMapping
    public List<PlanoAtividade> getAll() {
        return planoAtividadeService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlanoAtividade> getById(@PathVariable Long id) {
        return planoAtividadeService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/oportunidade/{oportunidadeId}")
    public ResponseEntity<PlanoAtividade> getByOportunidade(@PathVariable Long oportunidadeId) {
        return planoAtividadeService.getByOportunidade(oportunidadeId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public PlanoAtividade create(@RequestBody PlanoAtividade plano) {
        return planoAtividadeService.create(plano);
    }

    @PutMapping("/{id}")
    public PlanoAtividade update(@PathVariable Long id, @RequestBody PlanoAtividade plano) {
        return planoAtividadeService.update(id, plano);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        planoAtividadeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
