package Entidades;

import Enums.*;
import java.util.List;

public class Discente extends Usuario {
    private String matricula;
    private int semestre_atual;
    private Curso curso;

    private PPC_Historico ppcVinculado;

    public Discente(String nome, String email, String senha, Papel papel, Boolean ativo, String matricula,
            int semestre_atual, Curso curso) {
        super(nome, email, senha, papel, ativo);
        this.matricula = matricula;
        this.semestre_atual = semestre_atual;
        this.curso = curso;
    }

    // Getter & Setters

    public PPC_Historico getPpcVinculado() {
        return ppcVinculado;
    }

    /**
     * Permite vinculação explícita quando o PPC é cadastrado após o discente.
     */
    public void setPpcVinculado(PPC_Historico ppc) {
        this.ppcVinculado = ppc;
    }

    /**
     * Retorna a carga horária de extensão mínima exigida pelo PPC deste aluno.
     */
    public int getCargaHorariaExtensaoExigida() {
        if (ppcVinculado == null)
            throw new IllegalStateException("Discente " + getNome() + " não possui PPC vinculado.");
        return ppcVinculado.getCargaHorariaExtensao();
    }

    /**
     * Retorna as UCEs que este aluno deve cumprir (as do seu PPC).
     */
    public java.util.List<UCE> getUCEsDoSeuppc() {
        if (ppcVinculado == null)
            return new java.util.ArrayList<>();
        return ppcVinculado.getUces();
    }

    // --- Getters e Setters originais ---
    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public String getMatricula() {
        return matricula;
    }

    // Talvez Saia
    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public int getSemestre_atual() {
        return semestre_atual;
    }

    // Provavelmente vai sair
    public void setSemestre_atual(int semestre_atual) {
        this.semestre_atual = semestre_atual;
    }

    @Override
    public String toString() {
        String ppcInfo = ppcVinculado != null ? ppcVinculado.getVersao() : "sem PPC vinculado";
        return "Discente{nome='" + getNome() + '\'' +
                ", matricula='" + matricula + '\'' +
                ", curso='" + (curso != null ? curso.getNome() : "N/A") + '\'' +
                ", ppcVinculado='" + ppcInfo + '\'' +
                '}';
    }

}
