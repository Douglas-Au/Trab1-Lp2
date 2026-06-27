package edu.sge.sge.controller;

import edu.sge.sge.models.PPC_Historico;
import edu.sge.sge.services.PpcHistoricoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ppcs")
public class PpcHistoricoController {

    @Autowired
    private PpcHistoricoService ppcHistoricoService;

    @GetMapping
    public List<PPC_Historico> getAll() {
        return ppcHistoricoService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PPC_Historico> getById(@PathVariable Long id) {
        return ppcHistoricoService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/curso/{cursoId}")
    public List<PPC_Historico> getByCurso(@PathVariable Long cursoId) {
        return ppcHistoricoService.getByCurso(cursoId);
    }

    @PostMapping
    public PPC_Historico create(@RequestBody PPC_Historico ppc) {
        return ppcHistoricoService.create(ppc);
    }

    @PutMapping("/{id}")
    public PPC_Historico update(@PathVariable Long id, @RequestBody PPC_Historico ppc) {
        return ppcHistoricoService.update(id, ppc);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        ppcHistoricoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
