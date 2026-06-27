package edu.sge.sge.services;

import edu.sge.sge.models.DiscenteCursoHistorico;
import edu.sge.sge.repository.DiscenteCursoHistoricoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DiscenteCursoHistoricoService {

    @Autowired
    private DiscenteCursoHistoricoRepo repo;

    public List<DiscenteCursoHistorico> getAll() {
        return repo.findAll();
    }

    public Optional<DiscenteCursoHistorico> getById(Long id) {
        return repo.findById(id);
    }

    public List<DiscenteCursoHistorico> getByDiscente(Long discenteId) {
        return repo.findByDiscenteId(discenteId);
    }

    public DiscenteCursoHistorico create(DiscenteCursoHistorico historico) {
        return repo.save(historico);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}
