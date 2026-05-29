package Services;

import Repository.*;
import Entidades.*;
import Enums.*;

import java.time.LocalDate;

public class GrupoService {

    private final GrupoRepository grupoRepo;

    public GrupoService(GrupoRepository grupoRepo) {
        this.grupoRepo = grupoRepo;
    }

    // retorna o Grupo criado em vez de void
    public Grupo createGrupo(String nome, TipoGrupo tipo, String email, Docente responsavel, String descricao) {
        Grupo g = new Grupo(nome, tipo, email, descricao, statusGrupo.Ativo, responsavel);
        grupoRepo.addGrupo(responsavel, g);
        return g;
    }

    public void adicionarMembro(Grupo grupo, Discente discente) {
        Grupo grupoSalvo = grupoRepo.getGrupoFromDocente(grupo.getResponsavel(), grupo.getNome());

        if (grupoSalvo == null) {
            throw new IllegalArgumentException("Grupo não encontrado");
        }

        grupoSalvo.adicionarMembro(discente, GrupoFunc.MEMBRO, LocalDate.now());
    }

    public void removerMembro(Grupo grupo, Discente discente) {
        Grupo grupoSalvo = grupoRepo.getGrupoFromDocente(grupo.getResponsavel(), grupo.getNome());

        if (grupoSalvo == null) {
            throw new IllegalArgumentException("Grupo não encontrado");
        }

        grupoSalvo.removerMembro(discente);
    }

    public void encerrarGrupo(Grupo grupo) {
        Grupo grupoSalvo = grupoRepo.getGrupoFromDocente(grupo.getResponsavel(), grupo.getNome());

        if (grupoSalvo == null) {
            throw new IllegalArgumentException("Grupo não encontrado");
        }

        grupoSalvo.setStatus(statusGrupo.Inativo);
    }
}