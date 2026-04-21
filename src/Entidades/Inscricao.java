package Entidades;

public class Inscricao {
    enum Status{Pendente,Aprovado,Rejeitado}

    private Oportunidade oportunidade;
    private Discente discente;
    private Status status;
    private String motivacao;

    public void aprovar(){
        //TODO
    }

    public void rejeitar(){
        //TODO
    }

    public Inscricao(Oportunidade oportunidade, Discente discente, Status status, String motivacao) {
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getMotivacao() {
        return motivacao;
    }

    public void setMotivacao(String motivacao) {
        this.motivacao = motivacao;
    }
}
