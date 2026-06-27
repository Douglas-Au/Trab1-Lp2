package edu.sge.sge.controller;

import edu.sge.sge.models.Auditoria;
import edu.sge.sge.services.AuditoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auditorias")
public class AuditoriaController {

    @Autowired
    private AuditoriaService auditoriaService;

    @GetMapping
    public List<Auditoria> getAll() {
        return auditoriaService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Auditoria> getById(@PathVariable Long id) {
        return auditoriaService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/usuario/{usuarioId}")
    public List<Auditoria> getByUsuario(@PathVariable Long usuarioId) {
        return auditoriaService.getByUsuario(usuarioId);
    }
}
