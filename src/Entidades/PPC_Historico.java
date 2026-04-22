package Entidades;
import java.time.LocalDate;
import java.util.List;

public class PPC_Historico {
    private Curso curso;
    private String versao;
    private int cargaHorariaExtencao;
    private LocalDate dataVigenciaInicio;
    private LocalDate dataVigenciaFim;
    private Usuario autorAlteracao;
    private LocalDate dataCriacao;
    private LocalDate dataUpdate;

    public PPC_Historico(Curso curso, String versao, int cargaHorariaExtencao, LocalDate dataVigenciaInicio) {
        this.curso = curso;
        this.versao = versao;
        this.cargaHorariaExtencao = cargaHorariaExtencao;
        this.dataVigenciaInicio = dataVigenciaInicio;
    }

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

    public int getCargaHorariaExtencao() {
        return cargaHorariaExtencao;
    }

    public void setCargaHorariaExtencao(int cargaHorariaExtencao) {
        this.cargaHorariaExtencao = cargaHorariaExtencao;
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
}
