package Services;

import java.time.LocalDate;
import java.util.List;
import Enums.GrupoFunc;
import Repository.GrupoRepository;
import Entidades.Docente;
import Entidades.Discente;
import Entidades.Grupo;
import Entidades.MembroGrupo;
import Entidades.HistorioFuncGrupo;

public class MembroGrupoService {

    public void mudarFuncaoMembro(Docente responsavel, Grupo grupo, Discente aluno, GrupoFunc newFunc) {
        if (!grupo.getResponsavel().equals(responsavel)) {
            throw new IllegalStateException("Apenas o responsável pelo grupo pode alterar funções.");
        }

        List<MembroGrupo> membros = grupo.getMembros();
        boolean encontrado = false;
        for (MembroGrupo m : membros) {
            if (m.getDiscente().equals(aluno)) {
                // Encerra o período da função anterior no histórico
                LocalDate hoje = LocalDate.now();
                grupo.getHistoricoFuncoes().stream()
                        .filter(h -> h.getAluno().equals(aluno) && h.estaAtivo())
                        .forEach(h -> h.encerrarPeriodo(hoje));

                // Registra o início da nova função no histórico
                grupo.getHistoricoFuncoes().add(new HistorioFuncGrupo(aluno, newFunc, hoje));

                // Atualiza a função atual do membro
                m.setFunc(newFunc);
                m.setDataEntrada(hoje);
                encontrado = true;
                break;
            }
        }
        if (!encontrado) {
            throw new IllegalArgumentException("Aluno não encontrado no grupo.");
        }
    }
}
