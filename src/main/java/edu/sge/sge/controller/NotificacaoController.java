package edu.sge.sge.controller;

import edu.sge.sge.models.Notificacao;
import edu.sge.sge.services.NotificacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notificacoes")
public class NotificacaoController {

    @Autowired
    private NotificacaoService notificacaoService;

    @GetMapping
    public List<Notificacao> getAll() {
        return notificacaoService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Notificacao> getById(@PathVariable Long id) {
        return notificacaoService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/usuario/{usuarioId}")
    public List<Notificacao> getByUsuario(@PathVariable Long usuarioId) {
        return notificacaoService.getByUsuario(usuarioId);
    }

    @GetMapping("/usuario/{usuarioId}/nao-lidas")
    public List<Notificacao> getNaoLidas(@PathVariable Long usuarioId) {
        return notificacaoService.getNaoLidas(usuarioId);
    }

    @PostMapping
    public Notificacao create(@RequestBody Notificacao notificacao) {
        return notificacaoService.create(notificacao);
    }

    @PatchMapping("/{id}/lida")
    public ResponseEntity<?> marcarComoLida(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(notificacaoService.marcarComoLida(id));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        notificacaoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
