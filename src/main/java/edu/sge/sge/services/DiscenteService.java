package edu.sge.sge.services;

import edu.sge.sge.enums.Papel;
import edu.sge.sge.models.Discente;
import edu.sge.sge.repository.DiscenteRepo;
import edu.sge.sge.repository.UsuarioRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class DiscenteService {

    private static final Set<Papel> PAPEIS_VALIDOS = Set.of(Papel.DISCENTE, Papel.DISCENTE_DIRETOR);

    @Autowired
    private DiscenteRepo discenteRepo;

    @Autowired
    private UsuarioRepo usuarioRepo;

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
        var usuario = discente.getUsuario();
        if (usuario == null || usuario.getId() == null) {
            throw new IllegalArgumentException("Discente deve ter um usuário vinculado");
        }
        var usuarioPersistido = usuarioRepo.findById(usuario.getId())
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));
        if (!PAPEIS_VALIDOS.contains(usuarioPersistido.getPapel())) {
            throw new IllegalArgumentException(
                "Usuário com papel '" + usuarioPersistido.getPapel() + "' não pode ser registrado como Discente"
            );
        }
        if (discenteRepo.existsByUsuario(usuarioPersistido)) {
            throw new IllegalArgumentException("Usuário já está cadastrado como Discente");
        }
        discente.setUsuario(usuarioPersistido);
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
