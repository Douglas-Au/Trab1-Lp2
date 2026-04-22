package Entidades;
import java.util.ArrayList;
import java.util.List;

public class Curso {
    private String nome;
    private int codigo;
    private int carga_horaria;
    private String versao_ppc;

    private List<Discente> dicentes;

    public void atualizarPPC(int horas, String versao){
        carga_horaria = horas;
        versao_ppc = versao;
    }

    public Curso(String nome, int codigo, int carga_horaria, String versao_ppc) {
        this.nome = nome;
        this.codigo = codigo;
        this.carga_horaria = carga_horaria;
        this.versao_ppc = versao_ppc;
        this.dicentes = new ArrayList<>();
    }

    public List<Discente> getDicentes() {
        return dicentes;
    }

    public void setDicentes(List<Discente> dicentes) {
        this.dicentes = dicentes;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public int getCarga_horaria() {
        return carga_horaria;
    }

    public void setCarga_horaria(int carga_horaria) {
        this.carga_horaria = carga_horaria;
    }

    public String getVersao_ppc() {
        return versao_ppc;
    }

    public void setVersao_ppc(String versao_ppc) {
        this.versao_ppc = versao_ppc;
    }

    @Override
    public String toString() {
        return "Curso{" +
                "nome='" + nome + '\'' +
                ", codigo=" + codigo +
                ", carga_horaria=" + carga_horaria +
                ", versao_ppc='" + versao_ppc + '\'' +
                ", dicentes=" + dicentes +
                '}';
    }
}
