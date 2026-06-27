package edu.sge.sge.services;

import edu.sge.sge.enums.Papel;
import edu.sge.sge.models.Discente;
import edu.sge.sge.models.Usuario;
import edu.sge.sge.repository.CoordenadorRepo;
import edu.sge.sge.repository.DiscenteRepo;
import edu.sge.sge.repository.UsuarioRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import edu.sge.sge.models.CoordenadorUCE;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class CoordenadorService {

    @Autowired
    private CoordenadorRepo coordenadorRepo;

    @Autowired
    private UsuarioRepo usuarioRepo;

    private static final Set<Papel> PAPEIS_VALIDOS = Set.of(Papel.COORD_UCE, Papel.COORD_CURSO);


    public List<CoordenadorUCE> getAll() {
        return coordenadorRepo.findAll();
    }

    public Optional<CoordenadorUCE> getById(Long id) {
        return coordenadorRepo.findById(id);
    }

    public CoordenadorUCE create(CoordenadorUCE coordenador) {
        var usuario = coordenador.getUsuario();
        if (usuario == null || usuario.getId() == null) {
            throw new IllegalArgumentException("Coordenador deve ter um usuário vinculado");
        }
        var usuarioPersistido = usuarioRepo.findById(usuario.getId())
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));
        if (!PAPEIS_VALIDOS.contains(usuarioPersistido.getPapel())) {
            throw new IllegalArgumentException(
                    "Usuário com papel '" + usuarioPersistido.getPapel() + "' não pode ser registrado como Coordenador"
            );
        }
        if (coordenadorRepo.existsByUsuario(usuarioPersistido)) {
            throw new IllegalArgumentException("Usuário já está cadastrado como Coordenador");
        }
        coordenador.setUsuario(usuarioPersistido);
        return coordenadorRepo.save(coordenador);
    }

    public CoordenadorUCE update(Long id, CoordenadorUCE novoCoordenador) {
        novoCoordenador.setId(id);
        return coordenadorRepo.save(novoCoordenador);
    }

    public void delete(Long id) {
        coordenadorRepo.deleteById(id);
    }


}
