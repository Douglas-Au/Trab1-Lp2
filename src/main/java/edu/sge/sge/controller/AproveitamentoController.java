package edu.sge.sge.controller;

import edu.sge.sge.models.Aproveitamento;
import edu.sge.sge.models.AproveitamentoHistorico;
import edu.sge.sge.services.AproveitamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/aproveitamentos")
public class AproveitamentoController {

    @Autowired
    private AproveitamentoService aproveitamentoService;

    @GetMapping
    public List<Aproveitamento> getAll() {
        return aproveitamentoService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Aproveitamento> getById(@PathVariable Long id) {
        return aproveitamentoService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/discente/{discenteId}")
    public List<Aproveitamento> getByDiscente(@PathVariable Long discenteId) {
        return aproveitamentoService.getByDiscente(discenteId);
    }

    @GetMapping("/{id}/historico")
    public List<AproveitamentoHistorico> getHistorico(@PathVariable Long id) {
        return aproveitamentoService.getHistorico(id);
    }

    @PostMapping
    public ResponseEntity<?> solicitar(@RequestBody Aproveitamento aproveitamento) {
        try {
            return ResponseEntity.ok(aproveitamentoService.solicitar(aproveitamento));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/{id}/iniciar-analise")
    public ResponseEntity<?> iniciarAnalise(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(aproveitamentoService.iniciarAnalise(id));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/{id}/deferir")
    public ResponseEntity<?> deferir(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(aproveitamentoService.deferir(id));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/{id}/indeferir")
    public ResponseEntity<?> indeferir(@PathVariable Long id, @RequestParam String motivoRejeicao) {
        try {
            return ResponseEntity.ok(aproveitamentoService.indeferir(id, motivoRejeicao));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/{id}/reenviar")
    public ResponseEntity<?> reenviar(@PathVariable Long id, @RequestBody Aproveitamento dadosAtualizados) {
        try {
            return ResponseEntity.ok(aproveitamentoService.reenviar(id, dadosAtualizados));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/{id}/cancelar")
    public ResponseEntity<?> cancelar(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(aproveitamentoService.cancelar(id));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
