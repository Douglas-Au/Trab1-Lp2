package edu.sge.sge.services;


import edu.sge.sge.models.GrupoMembro;
import edu.sge.sge.models.Usuario;
import edu.sge.sge.repository.GrupoMembroRepo;
import edu.sge.sge.repository.GrupoRepo;
import edu.sge.sge.repository.UsuarioRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import edu.sge.sge.models.Grupo;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class GrupoService {

    @Autowired
    private GrupoRepo grupoRepo;

    @Autowired
    private GrupoMembroRepo grupoMembroRepo;

    @Autowired
    private UsuarioRepo usuarioRepo;

    public List<Grupo> getAll() {
        return grupoRepo.findAll();
    }

    public Grupo createGrupo(Grupo grupo) {
        return grupoRepo.save(grupo);
    }

    public Grupo updateGrupo(Long id, Grupo newGrupo) {
        newGrupo.setId(id);
        return grupoRepo.save(newGrupo);
    }

    public void deleteGrupo(Long id) {
        grupoRepo.deleteById(id);
    }

    public Optional<Grupo> getGrupo(Long id) {
        return grupoRepo.findById(id);
    }

    public List<GrupoMembro> listarMembros(Long grupoId) {
        return grupoMembroRepo.findByGrupoId(grupoId);
    }

    public GrupoMembro adicionarMembro(Long grupoId, Long usuarioId) {
        Grupo grupo = grupoRepo.findById(grupoId)
                .orElseThrow(() -> new IllegalArgumentException("Grupo não encontrado"));
        Usuario usuario = usuarioRepo.findById(usuarioId)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));
        if (grupoMembroRepo.existsByGrupoIdAndUsuarioIdAndDataSaidaIsNull(grupoId, usuarioId)) {
            throw new IllegalArgumentException("Usuário já é membro ativo deste grupo");
        }
        GrupoMembro membro = new GrupoMembro();
        membro.setGrupo(grupo);
        membro.setUsuario(usuario);
        membro.setDataEntrada(LocalDate.now());
        return grupoMembroRepo.save(membro);
    }
}
