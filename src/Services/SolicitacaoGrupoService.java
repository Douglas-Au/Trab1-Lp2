package Services;

import Entidades.*;
import Enums.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SolicitacaoGrupoService {

    private final GrupoService grupoService;
    private final List<SolicitacaoGrupo> solicitacoes = new ArrayList<>();

    public SolicitacaoGrupoService(GrupoService grupoService) {
        this.grupoService = grupoService;
    }

    // Ação do Discente

    /**
     * Discente solicita a criação de um grupo estudantil.
     * Todos os campos do formulário são obrigatórios.
     */
    public SolicitacaoGrupo solicitarCriacaoGrupo(Discente solicitante,
            Docente responsavel,
            String nomeGrupo,
            String descricao,
            String objetivos,
            TipoGrupo tipo,
            String emailGrupo) {
        validarDiscente(solicitante);

        SolicitacaoGrupo sol = new SolicitacaoGrupo(
                solicitante, responsavel, nomeGrupo, descricao, objetivos, tipo, emailGrupo);
        solicitacoes.add(sol);
        System.out.println("[SolicitacaoGrupoService] Solicitação #" + sol.getId()
                + " registrada e aguardando aprovação.");
        return sol;
    }

    // Ações do Coordenador

    /**
     * Aprova a solicitação e cria o grupo automaticamente.
     */
    public Grupo aprovar(Usuario decisor, SolicitacaoGrupo solicitacao) {
        validarDecisor(decisor);
        validarPendente(solicitacao);

        Grupo grupo = grupoService.createGrupo(
                solicitacao.getNomeGrupo(),
                solicitacao.getTipo(),
                solicitacao.getEmailGrupo(),
                solicitacao.getResponsavel(),
                solicitacao.getDescricao());

        solicitacao.aprovar(decisor, grupo);
        System.out.println("[SolicitacaoGrupoService] Solicitação #" + solicitacao.getId()
                + " aprovada por " + decisor.getNome() + ". Grupo criado.");
        return grupo;
    }

    /**
     * Rejeita a solicitação com um motivo obrigatório.
     */
    public void rejeitar(Usuario decisor, SolicitacaoGrupo solicitacao, String motivo) {
        validarDecisor(decisor);
        validarPendente(solicitacao);
        if (motivo == null || motivo.isBlank())
            throw new IllegalArgumentException("Motivo da rejeição é obrigatório.");

        solicitacao.rejeitar(decisor, motivo);
        System.out.println("[SolicitacaoGrupoService] Solicitação #" + solicitacao.getId()
                + " rejeitada por " + decisor.getNome() + ". Motivo: " + motivo);
    }

    // Consultas

    public List<SolicitacaoGrupo> listarPendentes() {
        return solicitacoes.stream()
                .filter(s -> s.getStatus() == statusSolicitacaoGrupo.PENDENTE)
                .collect(Collectors.toList());
    }

    public List<SolicitacaoGrupo> listarTodasSolicitacoes() {
        return new ArrayList<>(solicitacoes);
    }

    public List<SolicitacaoGrupo> listarPorDiscente(Discente discente) {
        return solicitacoes.stream()
                .filter(s -> s.getSolicitante().equals(discente))
                .collect(Collectors.toList());
    }

    // Validações privadas

    private void validarDiscente(Discente discente) {
        if (discente == null)
            throw new IllegalArgumentException("Discente não pode ser nulo.");
        if (!discente.getAtivo())
            throw new IllegalStateException("Apenas discentes ativos podem solicitar criação de grupos.");
    }

    private void validarDecisor(Usuario decisor) {
        if (decisor == null)
            throw new IllegalArgumentException("Decisor não pode ser nulo.");
        if (!decisor.getAtivo())
            throw new IllegalStateException("Usuário inativo não pode tomar decisões.");
        boolean ehAdmin = decisor.getPapel().equals(Papel.ADMIN);
        boolean ehCoordenador = decisor.getPapel().equals(Papel.CORD_CURSO);
        boolean ehCordUCE = decisor.getPapel().equals(Papel.CORD_UCE);
        if (!ehAdmin && !ehCoordenador && !ehCordUCE)
            throw new IllegalStateException(
                    "Apenas Administradores ou Coordenadores podem aprovar/rejeitar solicitações.");
    }

    private void validarPendente(SolicitacaoGrupo sol) {
        if (sol == null)
            throw new IllegalArgumentException("Solicitação não pode ser nula.");
        if (sol.getStatus() != statusSolicitacaoGrupo.PENDENTE)
            throw new IllegalStateException("Solicitação #" + sol.getId()
                    + " já foi processada (status: " + sol.getStatus() + ").");
    }
}
