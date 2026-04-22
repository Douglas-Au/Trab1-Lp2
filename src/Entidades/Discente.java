package Entidades;
import Enums.*;
import java.util.List;


public class Discente extends Usuario{
    private String matricula;
    private int semestre_atual;
    private Curso curso;

    public Discente(String nome, String email, String senha, Papel papel, Boolean ativo, String matricula, int semestre_atual, Curso curso) {
        super(nome, email, senha, papel, ativo);
        this.matricula = matricula;
        this.semestre_atual = semestre_atual;
        this.curso = curso;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
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
