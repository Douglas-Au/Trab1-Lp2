package Services;
import Entidades.*;
import Enums.Papel;

import java.util.ArrayList;
import java.util.List;

public class OpotunidadeService{
    private List<Oportunidade> oportunidades = new ArrayList<>();

    public Oportunidade criarOportunidade(Oportunidade oportunidade, Usuario usuario){
        if( !usuario.getPapel().equals(Papel.DOCENTE) && !usuario.getPapel().equals(Papel.DISCENTE_DIRETOR)){
            System.out.println("Permissão negada");
            return null;
        }
        oportunidades.add(oportunidade);
        return oportunidade;
    }

    public void divulgar(Oportunidade oportunidade, Usuario usuario){
        //TODO
    }

    public void atualizar(Oportunidade oportunidade){
        //TODO
    }

    public void remover(Oportunidade oportunidade){
        //TODO
    }

    public List<Oportunidade> listarTodas(){
        //TODO
        return null;
    }

    public List<Oportunidade> buscarPorStatus(Oportunidade status){
        //TODO
        return null;
    }
}
