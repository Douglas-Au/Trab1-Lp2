package Entidades;
import Enums.*;

public class Grupo {

    private String nome;
    private String tipo;
    private String email;
    private String descricao;
    private statusGrupo status;
    private Docente responsavel;

    public void adcionarMenbro(Usuario usuario){
        //TODO
    }

    public Grupo(String nome, String tipo, String email, String descricao, statusGrupo status, Docente responsavel) {
        this.nome = nome;
        this.tipo = tipo;
        this.email = email;
        this.descricao = descricao;
        this.status = status;
        this.responsavel = responsavel;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public statusGrupo getStatus() {
        return status;
    }

    public void setStatus(statusGrupo status) {
        this.status = status;
    }

    public Docente getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(Docente responsavel) {
        this.responsavel = responsavel;
    }
}
