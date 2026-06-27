package edu.sge.sge.services;

import edu.sge.sge.enums.Papel;
import edu.sge.sge.enums.StatusInscricao;
import edu.sge.sge.enums.StatusOportunidade;
import edu.sge.sge.models.Inscricao;
import edu.sge.sge.models.Oportunidade;
import edu.sge.sge.repository.DiscenteRepo;
import edu.sge.sge.repository.InscricaoRepo;
import edu.sge.sge.repository.OportunidadeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class InscricaoService {

    @Autowired
    private InscricaoRepo inscricaoRepo;

    @Autowired
    private OportunidadeRepo oportunidadeRepo;

    @Autowired
    private DiscenteRepo discenteRepo;

    @Autowired
    private AuthService authService;

    public List<Inscricao> getAll() {
        return inscricaoRepo.findAll();
    }

    public Optional<Inscricao> getById(Long id) {
        return inscricaoRepo.findById(id);
    }

    public List<Inscricao> getByOportunidade(Long oportunidadeId) {
        return inscricaoRepo.findByOportunidadeId(oportunidadeId);
    }

    public List<Inscricao> getByDiscente(Long discenteId) {
        return inscricaoRepo.findByDiscenteId(discenteId);
    }

    public Inscricao inscrever(Long oportunidadeId) {
        var usuario = authService.exigirPapel(Papel.DISCENTE, Papel.DISCENTE_DIRETOR);
        var discente = discenteRepo.findByUsuario(usuario)
                .orElseThrow(() -> new IllegalArgumentException("Usuário autenticado não possui cadastro de Discente"));
        var oportunidade = oportunidadeRepo.findById(oportunidadeId)
                .orElseThrow(() -> new IllegalArgumentException("Oportunidade não encontrada"));

        if (oportunidade.getStatus() != StatusOportunidade.ABERTA) {
            throw new IllegalArgumentException("Oportunidade não está com inscrições abertas");
        }
        validarJanelaInscricoes(oportunidade);
        if (inscricaoRepo.existsByOportunidadeIdAndDiscenteIdAndStatusNot(
                oportunidadeId, discente.getId(), StatusInscricao.CANCELADA)) {
            throw new IllegalArgumentException("Discente já possui inscrição ativa nesta oportunidade");
        }
        long aprovadas = inscricaoRepo.countByOportunidadeIdAndStatus(oportunidadeId, StatusInscricao.APROVADA);
        if (oportunidade.getVagas() > 0 && aprovadas >= oportunidade.getVagas()) {
            throw new IllegalArgumentException("Não há vagas disponíveis nesta oportunidade");
        }

        var inscricao = new Inscricao();
        inscricao.setOportunidade(oportunidade);
        inscricao.setDiscente(discente);
        inscricao.setStatus(StatusInscricao.PENDENTE);
        return inscricaoRepo.save(inscricao);
    }

    public Inscricao aprovar(Long id) {
        authService.exigirPapel(Papel.DISCENTE_DIRETOR, Papel.DOCENTE);
        var inscricao = buscarOuFalhar(id);
        exigirStatus(inscricao, StatusInscricao.PENDENTE);
        var oportunidade = inscricao.getOportunidade();
        long aprovadas = inscricaoRepo.countByOportunidadeIdAndStatus(oportunidade.getId(), StatusInscricao.APROVADA);
        if (oportunidade.getVagas() > 0 && aprovadas >= oportunidade.getVagas()) {
            throw new IllegalArgumentException("Não há vagas disponíveis para aprovar esta inscrição");
        }
        inscricao.setStatus(StatusInscricao.APROVADA);
        return inscricaoRepo.save(inscricao);
    }

    public Inscricao rejeitar(Long id) {
        authService.exigirPapel(Papel.DISCENTE_DIRETOR, Papel.DOCENTE);
        var inscricao = buscarOuFalhar(id);
        exigirStatus(inscricao, StatusInscricao.PENDENTE);
        inscricao.setStatus(StatusInscricao.REJEITADA);
        return inscricaoRepo.save(inscricao);
    }

    public Inscricao cancelar(Long id) {
        var usuario = authService.exigirPapel(Papel.DISCENTE, Papel.DISCENTE_DIRETOR);
        var inscricao = buscarOuFalhar(id);
        var donoId = inscricao.getDiscente().getUsuario().getId();
        if (!donoId.equals(usuario.getId())) {
            throw new IllegalArgumentException("Você só pode cancelar suas próprias inscrições");
        }
        if (inscricao.getStatus() == StatusInscricao.CANCELADA) {
            throw new IllegalArgumentException("Inscrição já está cancelada");
        }
        inscricao.setStatus(StatusInscricao.CANCELADA);
        return inscricaoRepo.save(inscricao);
    }

    private void validarJanelaInscricoes(Oportunidade oportunidade) {
        var hoje = LocalDate.now();
        if (oportunidade.getDataInicioInscricoes() != null
                && hoje.isBefore(oportunidade.getDataInicioInscricoes())) {
            throw new IllegalArgumentException("As inscrições ainda não começaram");
        }
        if (oportunidade.getDataFimInscricoes() != null
                && hoje.isAfter(oportunidade.getDataFimInscricoes())) {
            throw new IllegalArgumentException("O período de inscrições já encerrou");
        }
    }

    private Inscricao buscarOuFalhar(Long id) {
        return inscricaoRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Inscrição não encontrada"));
    }

    private void exigirStatus(Inscricao inscricao, StatusInscricao... permitidos) {
        for (var status : permitidos) {
            if (inscricao.getStatus() == status) {
                return;
            }
        }
        throw new IllegalArgumentException("Operação inválida para o status atual: " + inscricao.getStatus());
    }
}
