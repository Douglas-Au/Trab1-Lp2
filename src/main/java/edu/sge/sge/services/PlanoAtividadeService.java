package edu.sge.sge.services;

import edu.sge.sge.models.PlanoAtividade;
import edu.sge.sge.repository.PlanoAtividadeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlanoAtividadeService {

    @Autowired
    private PlanoAtividadeRepo planoAtividadeRepo;

    public List<PlanoAtividade> getAll() {
        return planoAtividadeRepo.findAll();
    }

    public Optional<PlanoAtividade> getById(Long id) {
        return planoAtividadeRepo.findById(id);
    }

    public Optional<PlanoAtividade> getByOportunidade(Long oportunidadeId) {
        return planoAtividadeRepo.findByOportunidadeId(oportunidadeId);
    }

    public PlanoAtividade create(PlanoAtividade plano) {
        return planoAtividadeRepo.save(plano);
    }

    public PlanoAtividade update(Long id, PlanoAtividade novoPlano) {
        novoPlano.setId(id);
        return planoAtividadeRepo.save(novoPlano);
    }

    public void delete(Long id) {
        planoAtividadeRepo.deleteById(id);
    }
}
