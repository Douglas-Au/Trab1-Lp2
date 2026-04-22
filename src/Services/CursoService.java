package Services;
import Entidades.*;
import Enums.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CursoService {
    private List<Curso> cursos = new ArrayList<>();

    public List<Discente> listarAlunosPorStatus(Curso curso, Boolean status){
        List<Discente> lista = curso.getDicentes().stream().filter(p -> p.getAtivo().equals(status))
                .collect(Collectors.toList());
        return lista;
    }

    public void mudarCurso(Discente discente, Curso novo_curso){
        Curso antigoCurso = discente.getCurso();

        antigoCurso.getDicentes().remove(discente);
        novo_curso.getDicentes().add(discente);
    }

}
