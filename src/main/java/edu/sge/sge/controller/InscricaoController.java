package edu.sge.sge.controller;

import edu.sge.sge.models.Inscricao;
import edu.sge.sge.services.InscricaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inscricoes")
public class InscricaoController {

    @Autowired
    private InscricaoService inscricaoService;

    @GetMapping
    public List<Inscricao> getAll() {
        return inscricaoService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Inscricao> getById(@PathVariable Long id) {
        return inscricaoService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/oportunidade/{oportunidadeId}")
    public List<Inscricao> getByOportunidade(@PathVariable Long oportunidadeId) {
        return inscricaoService.getByOportunidade(oportunidadeId);
    }

    @GetMapping("/discente/{discenteId}")
    public List<Inscricao> getByDiscente(@PathVariable Long discenteId) {
        return inscricaoService.getByDiscente(discenteId);
    }

    @PostMapping
    public ResponseEntity<?> inscrever(@RequestParam Long oportunidadeId) {
        try {
            return ResponseEntity.ok(inscricaoService.inscrever(oportunidadeId));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/{id}/aprovar")
    public ResponseEntity<?> aprovar(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(inscricaoService.aprovar(id));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/{id}/rejeitar")
    public ResponseEntity<?> rejeitar(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(inscricaoService.rejeitar(id));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/{id}/cancelar")
    public ResponseEntity<?> cancelar(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(inscricaoService.cancelar(id));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
