package edu.sge.sge.controller;

import edu.sge.sge.models.Certificado;
import edu.sge.sge.services.CertificadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/certificados")
public class CertificadoController {

    @Autowired
    private CertificadoService certificadoService;

    @GetMapping
    public List<Certificado> getAll() {
        return certificadoService.getAll();
    }

    @GetMapping("/discente/{discenteId}")
    public List<Certificado> getByDiscente(@PathVariable Long discenteId) {
        return certificadoService.getByDiscente(discenteId);
    }

    // Endpoint público de validação — não exige autenticação
    @GetMapping("/validar/{uuidHash}")
    public ResponseEntity<Certificado> validar(@PathVariable String uuidHash) {
        return certificadoService.validar(uuidHash)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> emitir(@RequestBody Certificado certificado) {
        try {
            return ResponseEntity.ok(certificadoService.emitir(certificado));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
