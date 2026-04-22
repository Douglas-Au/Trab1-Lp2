package Services;

import Entidades.*;

import java.util.ArrayList;
import java.util.List;

public class GrupoService {
    private List<Grupo> grupos = new ArrayList<>();

    public void adcionarMenbro(Grupo grupo,  Discente discente){
        grupo.getDiscentes().add(discente);
    }
}
