package Repository;

import java.util.HashMap;
import java.util.Map;

import Entidades.Grupo;
import Entidades.Docente;

public class GrupoRepository {
    private Map<String, Map<String, Grupo>> grupoRepository;

    public GrupoRepository() {
        this.grupoRepository = new HashMap<>();
    }

    public void addGrupo(Docente docente, Grupo g) {
        String nome = docente.getNome();
        if (nome != null) {
            grupoRepository.putIfAbsent(nome, new HashMap<>());
            grupoRepository.get(nome).put(g.getNome(), g);
        }

    }

    public void removeGrupo(Docente docente, Grupo g) {
        String nome = docente.getNome();
        if (grupoRepository.containsKey(nome)) {
            grupoRepository.get(nome).remove(g.getNome());
        }
    }

    public Grupo getGrupoFromDocente(
            Docente docente,
            String nomeGrupo) {

        Map<String, Grupo> grupos = grupoRepository.get(docente.getNome());

        if (grupos == null) {
            return null;
        }

        return grupos.get(nomeGrupo);
    }

}
