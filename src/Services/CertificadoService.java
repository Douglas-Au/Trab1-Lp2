package Services;
import Entidades.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CertificadoService {
    private List<Certificado> certificados = new ArrayList<>();

    public Certificado emitir(Discente discente, Oportunidade oportunidade, Usuario usuario){
        Certificado certificado = new Certificado();

        certificado.setDiscente(discente);
        certificado.setOportunidade(oportunidade);
        certificado.setUuid_hash("//TODO");
        certificados.add(certificado);

        return certificado;
    }

    public Boolean validarAutentcidade(String uuid){
        for(Certificado c: certificados){
            if(c.getUuid_hash().equals(uuid)){
                return true;
            }
        }
        return false;
    }

    public List<Certificado> CertificadosDiscente(Discente discente){

        List<Certificado> resultado = new ArrayList<>();

        resultado = certificados.stream()
                .filter(p -> p.getDiscente().equals(discente))
                .collect(Collectors.toList());

        return resultado;
    }

    public void solicitarEmissao(Discente discente, Oportunidade oportunidade){
        //TODO
    }


}
