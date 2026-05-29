package Entidades;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PPC_Historico {
    private Curso curso;
    private String versao;
    private int cargaHorariaExtensao;
    private LocalDate dataVigenciaInicio;
    private LocalDate dataVigenciaFim;
    private Usuario autorAlteracao;
    private LocalDate dataCriacao;
    private LocalDate dataUpdate;

    private List<UCE> uces = new ArrayList<>();

    public PPC_Historico(Curso curso, String versao, int cargaHorariaExtensao,
            LocalDate dataVigenciaInicio, Usuario autorAlteracao) {
        this.curso = curso;
        this.versao = versao;
        this.cargaHorariaExtensao = cargaHorariaExtensao;
        this.dataVigenciaInicio = dataVigenciaInicio;
        this.autorAlteracao = autorAlteracao;
        this.dataCriacao = LocalDate.now();
        this.dataUpdate = LocalDate.now();

    }

    public void adicionarUCE(UCE uce) {
        if (uce == null)
            throw new IllegalArgumentException("UCE não pode ser nula.");
        if (!uce.getPpc().equals(this))
            throw new IllegalArgumentException("A UCE informada não pertence a este PPC.");
        uces.add(uce);
        this.dataUpdate = LocalDate.now();
    }

    public int getCargaHorariaTotalUCEs() {
        return uces.stream().mapToInt(UCE::getCargaHoraria).sum();
    }

    public boolean cargaHorariaMinimaSatisfeita() {
        return getCargaHorariaTotalUCEs() >= cargaHorariaExtensao;
    }

    public List<UCE> getUces() {
        return uces;
    }

    // Getters e Setters
    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public String getVersao() {
        return versao;
    }

    public void setVersao(String versao) {
        this.versao = versao;
    }

    public int getCargaHorariaExtensao() {
        return cargaHorariaExtensao;
    }

    public void setCargaHorariaExtensao(int cargaHorariaExtensao) {
        this.cargaHorariaExtensao = cargaHorariaExtensao;
    }

    public LocalDate getDataVigenciaInicio() {
        return dataVigenciaInicio;
    }

    public void setDataVigenciaInicio(LocalDate dataVigenciaInicio) {
        this.dataVigenciaInicio = dataVigenciaInicio;
    }

    public LocalDate getDataVigenciaFim() {
        return dataVigenciaFim;
    }

    public void setDataVigenciaFim(LocalDate dataVigenciaFim) {
        this.dataVigenciaFim = dataVigenciaFim;
    }

    public Usuario getAutorAlteracao() {
        return autorAlteracao;
    }

    public void setAutorAlteracao(Usuario autorAlteracao) {
        this.autorAlteracao = autorAlteracao;
    }

    public LocalDate getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDate dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public LocalDate getDataUpdate() {
        return dataUpdate;
    }

    public void setDataUpdate(LocalDate dataUpdate) {
        this.dataUpdate = dataUpdate;
    }

    @Override
    public String toString() {
        return "PPC_Historico{" +
                "versao='" + versao + '\'' +
                ", cargaHorariaExtensao=" + cargaHorariaExtensao +
                ", dataVigenciaInicio=" + dataVigenciaInicio +
                ", dataVigenciaFim=" + (dataVigenciaFim != null ? dataVigenciaFim : "vigente") +
                ", autor=" + (autorAlteracao != null ? autorAlteracao.getNome() : "N/A") +
                ", dataCriacao=" + dataCriacao +
                '}';
    }
}
