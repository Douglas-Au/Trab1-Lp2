package Entidades;
import Enums.*;

public class Inscricao {

    private Oportunidade oportunidade;
    private Discente discente;
    private statusInscricao status;
    private String motivacao;

    public void aprovar(){
        //TODO
    }

    public void rejeitar(){
        //TODO
    }

    public Inscricao(Oportunidade oportunidade, Discente discente, statusInscricao status, String motivacao) {
        this.oportunidade = oportunidade;
        this.discente = discente;
        this.status = status;
        this.motivacao = motivacao;
    }

    public Oportunidade getOportunidade() {
        return oportunidade;
    }

    public void setOportunidade(Oportunidade oportunidade) {
        this.oportunidade = oportunidade;
    }

    public Discente getDiscente() {
        return discente;
    }

    public void setDiscente(Discente discente) {
        this.discente = discente;
    }

    public statusInscricao getStatus() {
        return status;
    }

    public void setStatus(statusInscricao status) {
        this.status = status;
    }

    public String getMotivacao() {
        return motivacao;
    }

    public void setMotivacao(String motivacao) {
        this.motivacao = motivacao;
    }

    @Override
    public String toString() {
        return "Inscricao{" +
                "oportunidade=" + oportunidade +
                ", discente=" + discente +
                ", status=" + status +
                ", motivacao='" + motivacao + '\'' +
                '}';
    }
}
