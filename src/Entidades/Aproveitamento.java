package Entidades;

import java.nio.file.Path;

public class Aproveitamento {
    private Discente discente;
    private String descricao;
    private String instituicao;
    private int horas;
    private String certificado_path;
    private Status status;
    private Usuario avaliador;
    private String motivo_rejeicao;

    public Boolean uploadCertificado(Path file){
        //TODO
        return true;
    }

    public Aproveitamento(Discente discente, String descricao, String instituicao, int horas, String certificado_path, Status status, Usuario avaliador, String motivo_rejeicao) {
        this.discente = discente;
        this.descricao = descricao;
        this.instituicao = instituicao;
        this.horas = horas;
        this.certificado_path = certificado_path;
        this.status = status;
        this.avaliador = avaliador;
        this.motivo_rejeicao = motivo_rejeicao;
    }

    public Discente getDiscente() {
        return discente;
    }

    public void setDiscente(Discente discente) {
        this.discente = discente;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getInstituicao() {
        return instituicao;
    }

    public void setInstituicao(String instituicao) {
        this.instituicao = instituicao;
    }

    public int getHoras() {
        return horas;
    }

    public void setHoras(int horas) {
        this.horas = horas;
    }

    public String getCertificado_path() {
        return certificado_path;
    }

    public void setCertificado_path(String certificado_path) {
        this.certificado_path = certificado_path;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Usuario getAvaliador() {
        return avaliador;
    }

    public void setAvaliador(Usuario avaliador) {
        this.avaliador = avaliador;
    }

    public String getMotivo_rejeicao() {
        return motivo_rejeicao;
    }

    public void setMotivo_rejeicao(String motivo_rejeicao) {
        this.motivo_rejeicao = motivo_rejeicao;
    }
}
