package Entidades;

import java.time.LocalDate;

public class Certificado {

    enum status_assinatura{Pendente, Assinado}

    private String uuid_hash;
    private Discente discente;
    private Oportunidade oportunidade;
    private LocalDate data;
    private int horas;
    private String certificado_path;

    public Certificado() {
        this.data = LocalDate.now();
    }

    public void gerarQRCode(){
        //TODO
    }

    public Boolean gerarAutenticidade(String hash){
        //TODO
        return true;
    }

    public String getUuid_hash() {
        return uuid_hash;
    }

    public void setUuid_hash(String uuid_hash) {
        this.uuid_hash = uuid_hash;
    }

    public Discente getDiscente() {
        return discente;
    }

    public void setDiscente(Discente discente) {
        this.discente = discente;
    }

    public Oportunidade getOportunidade() {
        return oportunidade;
    }

    public void setOportunidade(Oportunidade oportunidade) {
        this.oportunidade = oportunidade;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
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
}
