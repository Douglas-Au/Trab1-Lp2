package Entidades;
import java.time.LocalDate;
import Enums.*;
import java.util.List;

public class DiscenteDiretor extends Discente{
    private Grupo grupo;
    private String Cargo;
    private LocalDate data_inicio;
    private LocalDate data_fim;

    public DiscenteDiretor(String nome, String email, String senha, Papel papel, Boolean ativo, String matricula, int semestre_atual, Curso curso, Grupo grupo, String cargo) {
        super(nome, email, senha, papel, ativo, matricula, semestre_atual, curso);
        this.grupo = grupo;
        Cargo = cargo;
        data_inicio = LocalDate.now();
    }

    public Grupo getGrupo() {
        return grupo;
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }

    public String getCargo() {
        return Cargo;
    }

    public void setCargo(String cargo) {
        Cargo = cargo;
    }

    public LocalDate getData_inicio() {
        return data_inicio;
    }

    public void setData_inicio(LocalDate data_inicio) {
        this.data_inicio = data_inicio;
    }

    public LocalDate getData_fim() {
        return data_fim;
    }

    public void setData_fim(LocalDate data_fim) {
        this.data_fim = data_fim;
    }
}
