package Entidades;

import Enums.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Grupo {
    private String nome;
    private TipoGrupo tipo;
    private String email;
    private String descricao;
    private statusGrupo status;
    private Docente responsavel;
    private List<MembroGrupo> membros = new ArrayList<>();

    private List<HistorioFuncGrupo> historicoFuncoes = new ArrayList<>();

    public Grupo(String nome, TipoGrupo tipo, String email, String descricao, statusGrupo status, Docente responsavel) {
        this.nome = nome;
        this.tipo = tipo;
        this.email = email;
        this.descricao = descricao;
        this.status = status;
        this.responsavel = responsavel;
    }

    public void adicionarMembro(Discente aluno, GrupoFunc func, LocalDate dataInicio) {
        membros.add(new MembroGrupo(aluno, func, dataInicio));
        historicoFuncoes.add(new HistorioFuncGrupo(aluno, func, dataInicio));
    }

    public void removerMembro(Discente aluno) {
        // Encerra o período no cargo no histórico
        LocalDate hoje = LocalDate.now();
        historicoFuncoes.stream()
                .filter(h -> h.getAluno().equals(aluno) && h.estaAtivo())
                .forEach(h -> h.encerrarPeriodo(hoje));

        membros.removeIf(m -> m.getDiscente().equals(aluno));
    }

    public List<HistorioFuncGrupo> getHistoricoFuncoes() {
        return historicoFuncoes;
    }

    public List<HistorioFuncGrupo> getHistoricoFuncoesAluno(Discente aluno) {
        return historicoFuncoes.stream()
                .filter(h -> h.getAluno().equals(aluno))
                .collect(Collectors.toList());
    }

    public List<HistorioFuncGrupo> getHistoricoFuncao(GrupoFunc func) {
        return historicoFuncoes.stream()
                .filter(h -> h.getFuncao().equals(func))
                .collect(Collectors.toList());
    }

    public List<MembroGrupo> getMembros() {
        return membros;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public TipoGrupo getTipo() {
        return tipo;
    }

    public void setTipo(TipoGrupo tipo) {
        this.tipo = tipo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public statusGrupo getStatus() {
        return status;
    }

    public void setStatus(statusGrupo status) {
        this.status = status;
    }

    public Docente getResponsavel() {
        return responsavel;
    }

}
