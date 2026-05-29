package Entidades;

import Enums.GrupoFunc;
import java.time.LocalDate;

public class HistorioFuncGrupo {

    private GrupoFunc funcao;
    private Discente aluno;
    private LocalDate dataInicio;
    private LocalDate dataFim; // null = ainda no cargo

    public HistorioFuncGrupo(Discente aluno, GrupoFunc funcao, LocalDate dataInicio) {
        if (aluno == null)
            throw new IllegalArgumentException("Aluno não pode ser nulo.");
        if (funcao == null)
            throw new IllegalArgumentException("Função não pode ser nula.");
        if (dataInicio == null)
            throw new IllegalArgumentException("Data de início não pode ser nula.");

        this.aluno = aluno;
        this.funcao = funcao;
        this.dataInicio = dataInicio;
        this.dataFim = null;
    }

    /** Encerra o período neste cargo. */
    public void encerrarPeriodo(LocalDate dataFim) {
        if (dataFim == null || dataFim.isBefore(this.dataInicio)) {
            throw new IllegalArgumentException("Data de fim inválida.");
        }
        this.dataFim = dataFim;
    }

    public boolean estaAtivo() {
        return dataFim == null;
    }

    // Getters
    public GrupoFunc getFuncao() {
        return funcao;
    }

    public Discente getAluno() {
        return aluno;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public LocalDate getDataFim() {
        return dataFim;
    }

    @Override
    public String toString() {
        return "HistoricoFuncGrupo{" +
                "aluno='" + aluno.getNome() + '\'' +
                ", funcao=" + funcao +
                ", dataInicio=" + dataInicio +
                ", dataFim=" + (dataFim != null ? dataFim : "atual") +
                '}';
    }
}
