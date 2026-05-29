package Services;

import Entidades.Discente;
import Entidades.Docente;
import Entidades.Grupo;
import Entidades.Usuario;
import Enums.Papel;
import Enums.GrupoFunc;

public class DocenteService {

    private GrupoService grupoService;

    public DocenteService(GrupoService grupoService) {
        this.grupoService = grupoService;
    }

    public void adicionarMembro(Usuario docente, Grupo grupo, Discente discente) {
        validarDocente(docente);
        validarResponsabilidade(docente, grupo);

        grupoService.adicionarMembro(grupo, discente);
    }

    public void removerMembro(Usuario docente, Grupo grupo, Discente discente) {
        validarDocente(docente);
        validarResponsabilidade(docente, grupo);

        grupoService.removerMembro(grupo, discente);
    }

    public void encerrarGrupo(Usuario docente, Grupo grupo) {
        validarDocente(docente);
        validarResponsabilidade(docente, grupo);

        grupoService.encerrarGrupo(grupo);
    }

    public void mudarFuncaoMembro(Docente responsavel, Discente Discente, Grupo grupo, GrupoFunc novaFuncao) {
        validarDocente(responsavel);
        validarResponsabilidade(responsavel, grupo);

    }

    // validações privadas
    private void validarDocente(Usuario docente) {
        if (docente == null) {
            throw new IllegalArgumentException("Coordenador não pode ser nulo");
        }
        if (!docente.getAtivo()) {
            throw new IllegalStateException("Coordenador inativo não pode realizar esta ação");
        }
        if (!docente.getPapel().equals(Papel.DOCENTE)) {
            throw new IllegalStateException("Usuário não tem permissão de coordenador");
        }
    }

    private void validarResponsabilidade(Usuario docente, Grupo grupo) {
        if (!grupo.getResponsavel().equals(docente)) {
            throw new IllegalStateException("Docente não é responsável por este grupo");
        }
    }
}
