package edu.sge.sge.controller;

import edu.sge.sge.enums.StatusOportunidade;
import edu.sge.sge.models.Oportunidade;
import edu.sge.sge.services.OportunidadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/oportunidades")
public class OportunidadeController {

    @Autowired
    private OportunidadeService oportunidadeService;

    @GetMapping
    public List<Oportunidade> getAll() {
        return oportunidadeService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Oportunidade> getById(@PathVariable Long id) {
        return oportunidadeService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/status/{status}")
    public List<Oportunidade> getByStatus(@PathVariable StatusOportunidade status) {
        return oportunidadeService.getByStatus(status);
    }

    @PostMapping
    public ResponseEntity<?> criar(@RequestBody Oportunidade oportunidade) {
        try {
            return ResponseEntity.ok(oportunidadeService.criar(oportunidade));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/{id}/aprovar")
    public ResponseEntity<?> aprovar(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(oportunidadeService.aprovar(id));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/{id}/reprovar")
    public ResponseEntity<?> reprovar(@PathVariable Long id, @RequestParam String motivo) {
        try {
            return ResponseEntity.ok(oportunidadeService.reprovar(id, motivo));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/{id}/reenviar")
    public ResponseEntity<?> corrigirEReenviar(@PathVariable Long id, @RequestBody Oportunidade dados) {
        try {
            return ResponseEntity.ok(oportunidadeService.corrigirEReenviar(id, dados));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/{id}/abrir-inscricoes")
    public ResponseEntity<?> abrirInscricoes(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(oportunidadeService.abrirInscricoes(id));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/{id}/encerrar")
    public ResponseEntity<?> encerrar(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(oportunidadeService.encerrar(id));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/{id}/cancelar")
    public ResponseEntity<?> cancelar(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(oportunidadeService.cancelar(id));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
