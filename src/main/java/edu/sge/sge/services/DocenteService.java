package edu.sge.sge.services;

import edu.sge.sge.models.Docente;
import edu.sge.sge.repository.DocenteRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DocenteService {

    @Autowired
    private DocenteRepo docenteRepo;

    public List<Docente> getAll() {
        return docenteRepo.findAll();
    }

    public Optional<Docente> getById(Long id) {
        return docenteRepo.findById(id);
    }

    public Optional<Docente> getBySiape(String siape) {
        return docenteRepo.findBySiape(siape);
    }

    public List<Docente> getByDepartamento(String departamento) {
        return docenteRepo.findByDepartamento(departamento);
    }

    public Docente create(Docente docente) {
        return docenteRepo.save(docente);
    }

    public Docente update(Long id, Docente novoDocente) {
        novoDocente.setId(id);
        return docenteRepo.save(novoDocente);
    }

    public void delete(Long id) {
        docenteRepo.deleteById(id);
    }
}
