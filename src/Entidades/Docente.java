package Entidades;
import java.time.LocalDate;

public class Docente extends Usuario{
    private String siape;
    private String departamento;

    public void registrarPlanoAtiviades(Oportunidade oportunidade, LocalDate data){
        //TODO
        return;
    }
    public Docente(String nome, String email, String senha, Papel papel, Boolean ativo, String siape, String departamento) {
        super(nome, email, senha, "Docente", ativo);
        this.siape = siape;
        this.departamento = departamento;
    }

    public String getSiape() {
        return siape;
    }

    public void setSiape(String siape) {
        this.siape = siape;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }
}

