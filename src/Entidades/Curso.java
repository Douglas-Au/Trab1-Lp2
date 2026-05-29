package Entidades;

import java.util.ArrayList;
import java.util.List;

public class Curso {
    private String nome;
    private int codigo;
    private int carga_horaria;
    private String versao_ppc;

    private List<Discente> dicentes;

    private PPC_Historico ppcVigente;
    private List<PPC_Historico> historicoPPC;

    public Curso(String nome, int codigo, int carga_horaria, String versao_ppc) {
        this.nome = nome;
        this.codigo = codigo;
        this.carga_horaria = carga_horaria;
        this.versao_ppc = versao_ppc;
        this.dicentes = new ArrayList<>();
        this.historicoPPC = new ArrayList<>();
    }

    /*
     * public void atualizarPPC(int horas, String versao) {
     * carga_horaria = horas;
     * versao_ppc = versao;
     * }
     */

    public void cadastrarOuAtualizarPPC(PPC_Historico novoPPC) {
        if (novoPPC == null) {
            throw new IllegalArgumentException("PPC não pode ser nulo.");
        }

        // Encerra a vigência do PPC atual e o move para o histórico
        if (ppcVigente != null) {
            ppcVigente.setDataVigenciaFim(novoPPC.getDataVigenciaInicio().minusDays(1));
            historicoPPC.add(ppcVigente);
        }

        // Define o novo PPC como vigente e sincroniza campos legados
        ppcVigente = novoPPC;
        this.versao_ppc = novoPPC.getVersao();
        this.carga_horaria = novoPPC.getCargaHorariaExtensao();
    }

    public PPC_Historico getPpcVigente() {
        return ppcVigente;
    }

    public List<PPC_Historico> getHistoricoPPC() {
        return historicoPPC;
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
                ", ppcVigente=" + (ppcVigente != null ? ppcVigente.getVersao() : "N/A") +
                ", versoesHistorico=" + historicoPPC.size() +
                ", dicentes=" + dicentes +
                '}';
    }
}
