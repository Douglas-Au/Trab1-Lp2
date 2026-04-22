package Entidades;
import Enums.*;
import java.time.LocalDate;

public class Oportunidade {

    private String titulo;
    private String descricao;
    private statusOportunidade status;
    private TipoOportunidade tipo;
    private Modalidade modalidade;
    private int carga_horaria;
    private int vagas;
    private LocalDate inicio;
    private LocalDate fim;
    private Usuario autor;
    private Docente reponsavel;

    public void publicar(){
        //TODO
    }

    public void fecharInscricoes(){
        //TODO
    }

    public Oportunidade(String titulo, String descricao, statusOportunidade status, TipoOportunidade tipo, Modalidade modalidade, int carga_horaria, int vagas, LocalDate inicio, LocalDate fim, Usuario autor, Docente reponsavel) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.status = status;
        this.tipo = tipo;
        this.modalidade = modalidade;
        this.carga_horaria = carga_horaria;
        this.vagas = vagas;
        this.inicio = inicio;
        this.fim = fim;
        this.autor = autor;
        this.reponsavel = reponsavel;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public statusOportunidade getStatus() {
        return status;
    }

    public void setStatus(statusOportunidade status) {
        this.status = status;
    }

    public TipoOportunidade getTipo() {
        return tipo;
    }

    public void setTipo(TipoOportunidade tipo) {
        this.tipo = tipo;
    }

    public Modalidade getModalidade() {
        return modalidade;
    }

    public void setModalidade(Modalidade modalidade) {
        this.modalidade = modalidade;
    }

    public int getCarga_horaria() {
        return carga_horaria;
    }

    public void setCarga_horaria(int carga_horaria) {
        this.carga_horaria = carga_horaria;
    }

    public int getVagas() {
        return vagas;
    }

    public void setVagas(int vagas) {
        this.vagas = vagas;
    }

    public LocalDate getInicio() {
        return inicio;
    }

    public void setInicio(LocalDate inicio) {
        this.inicio = inicio;
    }

    public LocalDate getFim() {
        return fim;
    }

    public void setFim(LocalDate fim) {
        this.fim = fim;
    }

    public Usuario getAutor() {
        return autor;
    }

    public void setAutor(Usuario autor) {
        this.autor = autor;
    }

    public Docente getReponsavel() {
        return reponsavel;
    }

    public void setReponsavel(Docente reponsavel) {
        this.reponsavel = reponsavel;
    }
}
