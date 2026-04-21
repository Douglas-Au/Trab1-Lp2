package Services;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import Entidades.*;

public class AproveitamentoService {

    private List<Aproveitamento> aproveitamentos = new ArrayList<>();

    public Aproveitamento solicitar(Discente discente, Aproveitamento aproveitamento){
        aproveitamento.setDiscente(discente);
        //TODO
        // aproveitamento.setStatus(Aproveitamento.Status.Pendente);
        aproveitamentos.add(aproveitamento);

        return aproveitamento;
    }

    public void aprovar(Aproveitamento aproveitamento, Usuario usuario){
        //TODO aprovar
        //aproveitamento.setStatus();
    }

    public void rejeitar(Aproveitamento aproveitamento, Usuario usuario){
        //TODO rejeitar
        //aproveitamento.setStatus();
    }

    public List<Aproveitamento> aproveitamentosDiscente(Discente discente){

        List<Aproveitamento> resultado = new ArrayList<>();

        resultado = aproveitamentos.stream()
                .filter(p -> p.getDiscente().equals(discente))
                .collect(Collectors.toList());

        return resultado;
    }

    public List<Aproveitamento> getAproveitamentos() {
        return aproveitamentos;
    }
}
