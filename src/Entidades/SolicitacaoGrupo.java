package Entidades;

import Enums.TipoGrupo;
import Enums.statusSolicitacaoGrupo;
import java.time.LocalDate;

public class SolicitacaoGrupo {

    private static int contadorId = 1;

    private int id;
    private Discente solicitante; // quem pediu
    private Docente responsavel; // docente que será responsável pelo grupo
    private String nomeGrupo;
    private String descricao;
    private String objetivos;
    private TipoGrupo tipo;
    private String emailGrupo;

    private statusSolicitacaoGrupo status;
    private LocalDate dataSolicitacao;

    // Preenchidos após decisão
    private Usuario decisor; // coordenador que decidiu
    private LocalDate dataDecisao;
    private String motivoRejeicao; // null se aprovada

    // Grupo criado caso aprovada
    private Grupo grupoCriado;

    public SolicitacaoGrupo(Discente solicitante, Docente responsavel,
            String nomeGrupo, String descricao,
            String objetivos, TipoGrupo tipo, String emailGrupo) {
        validar(solicitante, responsavel, nomeGrupo, descricao, objetivos, emailGrupo);
        this.id = contadorId++;
        this.solicitante = solicitante;
        this.responsavel = responsavel;
        this.nomeGrupo = nomeGrupo;
        this.descricao = descricao;
        this.objetivos = objetivos;
        this.tipo = tipo;
        this.emailGrupo = emailGrupo;
        this.status = statusSolicitacaoGrupo.PENDENTE;
        this.dataSolicitacao = LocalDate.now();
    }

    private void validar(Discente solicitante, Docente responsavel,
            String nome, String descricao, String objetivos, String email) {
        if (solicitante == null)
            throw new IllegalArgumentException("Solicitante (discente) é obrigatório.");
        if (responsavel == null)
            throw new IllegalArgumentException("Docente responsável é obrigatório.");
        if (nome == null || nome.isBlank())
            throw new IllegalArgumentException("Nome do grupo é obrigatório.");
        if (descricao == null || descricao.isBlank())
            throw new IllegalArgumentException("Descrição do grupo é obrigatória.");
        if (objetivos == null || objetivos.isBlank())
            throw new IllegalArgumentException("Objetivos do grupo são obrigatórios.");
        if (email == null || email.isBlank())
            throw new IllegalArgumentException("E-mail do grupo é obrigatório.");
    }

    // --- Getters ---
    public int getId() {
        return id;
    }

    public Discente getSolicitante() {
        return solicitante;
    }

    public Docente getResponsavel() {
        return responsavel;
    }

    public String getNomeGrupo() {
        return nomeGrupo;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getObjetivos() {
        return objetivos;
    }

    public TipoGrupo getTipo() {
        return tipo;
    }

    public String getEmailGrupo() {
        return emailGrupo;
    }

    public statusSolicitacaoGrupo getStatus() {
        return status;
    }

    public LocalDate getDataSolicitacao() {
        return dataSolicitacao;
    }

    public Usuario getDecisor() {
        return decisor;
    }

    public LocalDate getDataDecisao() {
        return dataDecisao;
    }

    public String getMotivoRejeicao() {
        return motivoRejeicao;
    }

    public Grupo getGrupoCriado() {
        return grupoCriado;
    }

    // --- Setters internos (usados pelo Service) ---
    public void aprovar(Usuario decisor, Grupo grupo) {
        this.status = statusSolicitacaoGrupo.APROVADA;
        this.decisor = decisor;
        this.dataDecisao = LocalDate.now();
        this.grupoCriado = grupo;
    }

    public void rejeitar(Usuario decisor, String motivo) {
        this.status = statusSolicitacaoGrupo.REJEITADA;
        this.decisor = decisor;
        this.dataDecisao = LocalDate.now();
        this.motivoRejeicao = motivo;
    }

    @Override
    public String toString() {
        return "SolicitacaoGrupo{" +
                "id=" + id +
                ", solicitante='" + solicitante.getNome() + '\'' +
                ", nomeGrupo='" + nomeGrupo + '\'' +
                ", responsavel='" + responsavel.getNome() + '\'' +
                ", tipo=" + tipo +
                ", status=" + status +
                ", dataSolicitacao=" + dataSolicitacao +
                (decisor != null ? ", decisor='" + decisor.getNome() + '\'' : "") +
                (motivoRejeicao != null ? ", motivo='" + motivoRejeicao + '\'' : "") +
                '}';
    }
}
