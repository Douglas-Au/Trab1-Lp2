package edu.sge.sge.services;

import edu.sge.sge.models.Curso;
import edu.sge.sge.repository.CursoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CursoService {

    @Autowired
    private CursoRepo cursoRepo;

    public List<Curso> getAll() {
        return cursoRepo.findAll();
    }

    public Optional<Curso> getById(Long id) {
        return cursoRepo.findById(id);
    }

    public Optional<Curso> getByCodigo(String codigo) {
        return cursoRepo.findByCodigo(codigo);
    }

    public Curso create(Curso curso) {
        if (cursoRepo.existsByCodigo(curso.getCodigo())) {
            throw new IllegalArgumentException("Curso já cadastrado");
        }
        return cursoRepo.save(curso);
    }

    public Curso update(Long id, Curso novoCurso) {
        novoCurso.setId(id);
        return cursoRepo.save(novoCurso);
    }

    public void delete(Long id) {
        cursoRepo.deleteById(id);
    }
}
