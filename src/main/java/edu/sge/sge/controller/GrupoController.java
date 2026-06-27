package edu.sge.sge.controller;

import edu.sge.sge.models.Grupo;
import edu.sge.sge.models.GrupoMembro;
import edu.sge.sge.services.GrupoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/grupos")
public class GrupoController {

    @Autowired
    private GrupoService grupoService;

    @GetMapping
    public List<Grupo> getAll() {
        return grupoService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Grupo> getById(@PathVariable Long id) {
        return grupoService.getGrupo(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Grupo create(@RequestBody Grupo grupo) {
        return grupoService.createGrupo(grupo);
    }

    @PutMapping("/{id}")
    public Grupo update(@PathVariable Long id, @RequestBody Grupo grupo) {
        return grupoService.updateGrupo(id, grupo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        grupoService.deleteGrupo(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/membros")
    public List<GrupoMembro> listarMembros(@PathVariable Long id) {
        return grupoService.listarMembros(id);
    }

    @PostMapping("/{id}/membros")
    public ResponseEntity<?> adicionarMembro(@PathVariable Long id, @RequestParam Long usuarioId) {
        try {
            return ResponseEntity.ok(grupoService.adicionarMembro(id, usuarioId));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
