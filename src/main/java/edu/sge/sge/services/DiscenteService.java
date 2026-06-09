package edu.sge.sge.services;

import edu.sge.sge.models.Discente;
import edu.sge.sge.repository.DiscenteRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DiscenteService {

    @Autowired
    private DiscenteRepo discenteRepo;

    public List<Discente> getAll() {
        return discenteRepo.findAll();
    }

    public Optional<Discente> getById(Long id) {
        return discenteRepo.findById(id);
    }

    public Optional<Discente> getByMatricula(String matricula) {
        return discenteRepo.findByMatricula(matricula);
    }

    public Discente create(Discente discente) {
        return discenteRepo.save(discente);
    }

    public Discente update(Long id, Discente novoDiscente) {
        novoDiscente.setId(id);
        return discenteRepo.save(novoDiscente);
    }

    public void delete(Long id) {
        discenteRepo.deleteById(id);
    }
}
