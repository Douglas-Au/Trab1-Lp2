package Services;

import Entidades.Discente;
import Entidades.Inscricao;
import Entidades.Oportunidade;
import Entidades.Docente;
import Entidades.Oportunidade;
import Enums.statusInscricao;

import java.util.ArrayList;
import java.util.List;

public class InscricaoService {

    private List<Inscricao> inscricoes = new ArrayList<>();

    public Inscricao inscrever(Discente discente, Oportunidade oportunidade){
            Inscricao i = new Inscricao(oportunidade, discente, statusInscricao.Pendente, null);
            inscricoes.add(i);
        return i;
    }

    public void cancelar(Inscricao inscricao){
        //TODO
    }

    public void aprovar(Inscricao inscricao){
        //TODO
    }

    public void rejeitar(Inscricao inscricao){
        //TODO
    }
}
