package edu.sge.sge.services;

import edu.sge.sge.models.PPC_Historico;
import edu.sge.sge.repository.PpcHistoricoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PpcHistoricoService {

    @Autowired
    private PpcHistoricoRepo ppcHistoricoRepo;

    public List<PPC_Historico> getAll() {
        return ppcHistoricoRepo.findAll();
    }

    public Optional<PPC_Historico> getById(Long id) {
        return ppcHistoricoRepo.findById(id);
    }

    public List<PPC_Historico> getByCurso(Long cursoId) {
        return ppcHistoricoRepo.findByCursoId(cursoId);
    }

    public PPC_Historico create(PPC_Historico ppc) {
        return ppcHistoricoRepo.save(ppc);
    }

    public PPC_Historico update(Long id, PPC_Historico novoPpc) {
        novoPpc.setId(id);
        return ppcHistoricoRepo.save(novoPpc);
    }

    public void delete(Long id) {
        ppcHistoricoRepo.deleteById(id);
    }
}
