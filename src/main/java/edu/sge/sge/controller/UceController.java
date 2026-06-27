package edu.sge.sge.controller;

import edu.sge.sge.models.UCE;
import edu.sge.sge.services.UceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/uces")
public class UceController {

    @Autowired
    private UceService uceService;

    @GetMapping
    public List<UCE> getAll() {
        return uceService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UCE> getById(@PathVariable Long id) {
        return uceService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/ppc/{ppcId}")
    public List<UCE> getByPpc(@PathVariable Long ppcId) {
        return uceService.getByPpc(ppcId);
    }

    @PostMapping
    public UCE create(@RequestBody UCE uce) {
        return uceService.create(uce);
    }

    @PutMapping("/{id}")
    public UCE update(@PathVariable Long id, @RequestBody UCE uce) {
        return uceService.update(id, uce);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        uceService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
