package edu.sge.sge.services;

import edu.sge.sge.enums.Papel;
import edu.sge.sge.models.DiscenteDiretor;
import edu.sge.sge.repository.DiscenteDiretorRepo;
import edu.sge.sge.repository.DiscenteRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DiscenteDiretorService {

    @Autowired
    private DiscenteDiretorRepo discenteDiretorRepo;

    @Autowired
    private DiscenteRepo discenteRepo;

    public List<DiscenteDiretor> getAll() {
        return discenteDiretorRepo.findAll();
    }

    public Optional<DiscenteDiretor> getById(Long id) {
        return discenteDiretorRepo.findById(id);
    }

    public List<DiscenteDiretor> getByGrupo(Long grupoId) {
        return discenteDiretorRepo.findByGrupoId(grupoId);
    }

    public DiscenteDiretor create(DiscenteDiretor diretor) {
        var discente = diretor.getDiscente();
        if (discente == null || discente.getId() == null) {
            throw new IllegalArgumentException("DiscenteDiretor deve referenciar um Discente");
        }
        var discentePersistido = discenteRepo.findById(discente.getId())
                .orElseThrow(() -> new IllegalArgumentException("Discente não encontrado"));
        var papel = discentePersistido.getUsuario() != null ? discentePersistido.getUsuario().getPapel() : null;
        if (papel != Papel.DISCENTE_DIRETOR) {
            throw new IllegalArgumentException(
                    "Discente precisa ter papel DISCENTE_DIRETOR para ser registrado como diretor");
        }
        if (discenteDiretorRepo.existsByDiscenteId(discentePersistido.getId())) {
            throw new IllegalArgumentException("Discente já está registrado como diretor");
        }
        diretor.setDiscente(discentePersistido);
        return discenteDiretorRepo.save(diretor);
    }

    public DiscenteDiretor update(Long id, DiscenteDiretor novoDiretor) {
        novoDiretor.setId(id);
        return discenteDiretorRepo.save(novoDiretor);
    }

    public void delete(Long id) {
        discenteDiretorRepo.deleteById(id);
    }
}
