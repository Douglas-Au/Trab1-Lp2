package edu.sge.sge.services;

import edu.sge.sge.models.Comunicado;
import edu.sge.sge.repository.ComunicadoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ComunicadoService {

    @Autowired
    private ComunicadoRepo comunicadoRepo;

    public List<Comunicado> getAll() {
        return comunicadoRepo.findAll();
    }

    public Optional<Comunicado> getById(Long id) {
        return comunicadoRepo.findById(id);
    }

    public List<Comunicado> getByCurso(Long cursoId) {
        return comunicadoRepo.findByCursoId(cursoId);
    }

    public Comunicado create(Comunicado comunicado) {
        comunicado.setEnviadoEm(LocalDateTime.now());
        return comunicadoRepo.save(comunicado);
    }

    public void delete(Long id) {
        comunicadoRepo.deleteById(id);
    }
}
