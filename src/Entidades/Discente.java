package Entidades;
import java.util.List;


public class Discente extends Usuario{
    private String matricula;
    private int semestre_atual;
    private List<Curso> curso;

    public Discente(String nome, String email, String senha, Papel papel, Boolean ativo, String matricula, int semestre_atual, List<Curso> curso) {
        super(nome, email, senha, papel, ativo);
        this.matricula = matricula;
        this.semestre_atual = semestre_atual;
        this.curso = curso;
    }

    public String getMatricula() {
        return matricula;
    }

    //Talvez Saia
    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public int getSemestre_atual() {
        return semestre_atual;
    }

    //Provavelmente vai sair
    public void setSemestre_atual(int semestre_atual) {
        this.semestre_atual = semestre_atual;
    }
}
