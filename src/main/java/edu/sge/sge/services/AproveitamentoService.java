package edu.sge.sge.services;

import edu.sge.sge.enums.Papel;
import edu.sge.sge.enums.StatusAproveitamento;
import edu.sge.sge.models.Aproveitamento;
import edu.sge.sge.models.AproveitamentoHistorico;
import edu.sge.sge.models.Usuario;
import edu.sge.sge.repository.AproveitamentoHistoricoRepo;
import edu.sge.sge.repository.AproveitamentoRepo;
import edu.sge.sge.repository.DiscenteRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class AproveitamentoService {

    private static final int DIAS_PRAZO_REENVIO = 5;

    @Autowired
    private AproveitamentoRepo aproveitamentoRepo;

    @Autowired
    private AproveitamentoHistoricoRepo historicoRepo;

    @Autowired
    private DiscenteRepo discenteRepo;

    @Autowired
    private AuthService authService;

    public List<Aproveitamento> getAll() {
        return aproveitamentoRepo.findAll();
    }

    public Optional<Aproveitamento> getById(Long id) {
        return aproveitamentoRepo.findById(id);
    }

    public List<Aproveitamento> getByDiscente(Long discenteId) {
        return aproveitamentoRepo.findByDiscenteId(discenteId);
    }

    public List<AproveitamentoHistorico> getHistorico(Long aproveitamentoId) {
        return historicoRepo.findByAproveitamentoIdOrderByIdAsc(aproveitamentoId);
    }

    public Aproveitamento solicitar(Aproveitamento aproveitamento) {
        var usuario = authService.exigirPapel(Papel.DISCENTE, Papel.DISCENTE_DIRETOR);
        var discente = discenteRepo.findByUsuario(usuario)
                .orElseThrow(() -> new IllegalArgumentException("Usuário autenticado não possui cadastro de Discente"));
        if (aproveitamento.getCertificadoPath() == null || aproveitamento.getCertificadoPath().isBlank()) {
            throw new IllegalArgumentException("É obrigatório anexar o documento comprobatório (certificadoPath)");
        }
        aproveitamento.setDiscente(discente);
        aproveitamento.setStatus(StatusAproveitamento.PENDENTE);
        aproveitamento.setAvaliador(null);
        aproveitamento.setMotivoRejeicao(null);
        aproveitamento.setPrazoReenvio(null);
        var salvo = aproveitamentoRepo.save(aproveitamento);
        registrarHistorico(salvo, null, StatusAproveitamento.PENDENTE, null, "Solicitação criada");
        return salvo;
    }

    public Aproveitamento iniciarAnalise(Long id) {
        var avaliador = authService.exigirPapel(Papel.COORD_UCE);
        var aproveitamento = buscarOuFalhar(id);
        exigirStatusEntre(aproveitamento, StatusAproveitamento.PENDENTE);
        var statusAnterior = aproveitamento.getStatus();
        aproveitamento.setStatus(StatusAproveitamento.EM_ANALISE);
        aproveitamento.setAvaliador(avaliador);
        var salvo = aproveitamentoRepo.save(aproveitamento);
        registrarHistorico(salvo, statusAnterior, StatusAproveitamento.EM_ANALISE, avaliador, "Análise iniciada");
        return salvo;
    }

    public Aproveitamento deferir(Long id) {
        var avaliador = authService.exigirPapel(Papel.COORD_UCE);
        var aproveitamento = buscarOuFalhar(id);
        exigirStatusEntre(aproveitamento, StatusAproveitamento.PENDENTE, StatusAproveitamento.EM_ANALISE);
        var statusAnterior = aproveitamento.getStatus();
        aproveitamento.setStatus(StatusAproveitamento.APROVADO);
        aproveitamento.setAvaliador(avaliador);
        var salvo = aproveitamentoRepo.save(aproveitamento);
        registrarHistorico(salvo, statusAnterior, StatusAproveitamento.APROVADO, avaliador, "Horas aprovadas e creditadas");
        return salvo;
    }

    public Aproveitamento indeferir(Long id, String motivoRejeicao) {
        if (motivoRejeicao == null || motivoRejeicao.isBlank()) {
            throw new IllegalArgumentException("Indeferimento exige parecer escrito (motivoRejeicao)");
        }
        var avaliador = authService.exigirPapel(Papel.COORD_UCE);
        var aproveitamento = buscarOuFalhar(id);
        exigirStatusEntre(aproveitamento, StatusAproveitamento.PENDENTE, StatusAproveitamento.EM_ANALISE);
        var statusAnterior = aproveitamento.getStatus();
        aproveitamento.setStatus(StatusAproveitamento.AGUARDANDO_REENVIO);
        aproveitamento.setAvaliador(avaliador);
        aproveitamento.setMotivoRejeicao(motivoRejeicao);
        aproveitamento.setPrazoReenvio(LocalDate.now().plusDays(DIAS_PRAZO_REENVIO));
        var salvo = aproveitamentoRepo.save(aproveitamento);
        registrarHistorico(salvo, statusAnterior, StatusAproveitamento.AGUARDANDO_REENVIO, avaliador, motivoRejeicao);
        return salvo;
    }

    public Aproveitamento reenviar(Long id, Aproveitamento dadosAtualizados) {
        var usuario = authService.exigirPapel(Papel.DISCENTE, Papel.DISCENTE_DIRETOR);
        var aproveitamento = buscarOuFalhar(id);
        exigirDono(aproveitamento, usuario);
        verificarExpiracao(aproveitamento);
        exigirStatusEntre(aproveitamento, StatusAproveitamento.AGUARDANDO_REENVIO);
        aproveitamento.setDescricao(dadosAtualizados.getDescricao());
        aproveitamento.setInstituicao(dadosAtualizados.getInstituicao());
        aproveitamento.setHoras(dadosAtualizados.getHoras());
        aproveitamento.setCertificadoPath(dadosAtualizados.getCertificadoPath());
        aproveitamento.setDataAtividadeInicio(dadosAtualizados.getDataAtividadeInicio());
        aproveitamento.setDataAtividadeFim(dadosAtualizados.getDataAtividadeFim());
        var statusAnterior = aproveitamento.getStatus();
        aproveitamento.setStatus(StatusAproveitamento.PENDENTE);
        aproveitamento.setMotivoRejeicao(null);
        aproveitamento.setPrazoReenvio(null);
        var salvo = aproveitamentoRepo.save(aproveitamento);
        registrarHistorico(salvo, statusAnterior, StatusAproveitamento.PENDENTE, null, "Reenviado pelo discente");
        return salvo;
    }

    public Aproveitamento cancelar(Long id) {
        var usuario = authService.exigirPapel(Papel.DISCENTE, Papel.DISCENTE_DIRETOR);
        var aproveitamento = buscarOuFalhar(id);
        exigirDono(aproveitamento, usuario);
        var statusAnterior = aproveitamento.getStatus();
        if (statusAnterior == StatusAproveitamento.CANCELADO) {
            throw new IllegalArgumentException("Solicitação já está cancelada");
        }
        aproveitamento.setStatus(StatusAproveitamento.CANCELADO);
        var salvo = aproveitamentoRepo.save(aproveitamento);
        registrarHistorico(salvo, statusAnterior, StatusAproveitamento.CANCELADO, null, "Cancelado pelo discente");
        return salvo;
    }

    private void verificarExpiracao(Aproveitamento aproveitamento) {
        if (aproveitamento.getStatus() == StatusAproveitamento.AGUARDANDO_REENVIO
                && aproveitamento.getPrazoReenvio() != null
                && LocalDate.now().isAfter(aproveitamento.getPrazoReenvio())) {
            var statusAnterior = aproveitamento.getStatus();
            aproveitamento.setStatus(StatusAproveitamento.EXPIRADO);
            aproveitamentoRepo.save(aproveitamento);
            registrarHistorico(aproveitamento, statusAnterior, StatusAproveitamento.EXPIRADO, null, "Prazo de reenvio expirado");
            throw new IllegalArgumentException("Prazo de 5 dias para reenvio expirou");
        }
    }

    private Aproveitamento buscarOuFalhar(Long id) {
        return aproveitamentoRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Aproveitamento não encontrado"));
    }

    private void exigirDono(Aproveitamento aproveitamento, Usuario usuario) {
        var donoId = aproveitamento.getDiscente().getUsuario().getId();
        if (!donoId.equals(usuario.getId())) {
            throw new IllegalArgumentException("Você só pode operar suas próprias solicitações");
        }
    }

    private void exigirStatusEntre(Aproveitamento aproveitamento, StatusAproveitamento... permitidos) {
        for (var status : permitidos) {
            if (aproveitamento.getStatus() == status) {
                return;
            }
        }
        throw new IllegalArgumentException("Operação inválida para o status atual: " + aproveitamento.getStatus());
    }

    private void registrarHistorico(Aproveitamento aproveitamento, StatusAproveitamento anterior,
                                     StatusAproveitamento novo, Usuario avaliador, String observacao) {
        var historico = new AproveitamentoHistorico();
        historico.setAproveitamento(aproveitamento);
        historico.setStatusAnterior(anterior);
        historico.setStatusNovo(novo);
        historico.setAvaliador(avaliador);
        historico.setObservacao(observacao);
        historicoRepo.save(historico);
    }
}
