package edu.sge.sge.services;

import edu.sge.sge.enums.Papel;
import edu.sge.sge.models.Docente;
import edu.sge.sge.repository.DocenteRepo;
import edu.sge.sge.repository.UsuarioRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DocenteService {

    @Autowired
    private DocenteRepo docenteRepo;

    @Autowired
    private UsuarioRepo usuarioRepo;

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
        var usuario = docente.getUsuario();
        if (usuario == null || usuario.getId() == null) {
            throw new IllegalArgumentException("Docente deve ter um usuário vinculado");
        }
        var usuarioPersistido = usuarioRepo.findById(usuario.getId())
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));
        if (usuarioPersistido.getPapel() != Papel.DOCENTE) {
            throw new IllegalArgumentException(
                "Usuário com papel '" + usuarioPersistido.getPapel() + "' não pode ser registrado como Docente"
            );
        }
        if (docenteRepo.existsByUsuario(usuarioPersistido)) {
            throw new IllegalArgumentException("Usuário já está cadastrado como Docente");
        }
        docente.setUsuario(usuarioPersistido);
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
