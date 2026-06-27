package edu.sge.sge.services;

import edu.sge.sge.enums.Papel;
import edu.sge.sge.enums.StatusOportunidade;
import edu.sge.sge.models.Oportunidade;
import edu.sge.sge.repository.DocenteRepo;
import edu.sge.sge.repository.OportunidadeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OportunidadeService {

    @Autowired
    private OportunidadeRepo oportunidadeRepo;

    @Autowired
    private DocenteRepo docenteRepo;

    @Autowired
    private AuthService authService;

    public List<Oportunidade> getAll() {
        return oportunidadeRepo.findAll();
    }

    public Optional<Oportunidade> getById(Long id) {
        return oportunidadeRepo.findById(id);
    }

    public List<Oportunidade> getByStatus(StatusOportunidade status) {
        return oportunidadeRepo.findByStatus(status);
    }

    public Oportunidade criar(Oportunidade oportunidade) {
        var usuario = authService.exigirPapel(Papel.DISCENTE_DIRETOR);
        oportunidade.setStatus(StatusOportunidade.AGUARDANDO_APROVACAO);
        oportunidade.setResponsavelTipo("DISCENTE_DIRETOR");
        oportunidade.setResponsavelId(usuario.getId());
        oportunidade.setDocenteValidador(null);
        return oportunidadeRepo.save(oportunidade);
    }

    public Oportunidade aprovar(Long id) {
        var usuario = authService.exigirPapel(Papel.DOCENTE);
        var oportunidade = buscarOuFalhar(id);
        exigirStatusEntre(oportunidade, StatusOportunidade.AGUARDANDO_APROVACAO);
        var docente = docenteRepo.findByUsuario(usuario)
                .orElseThrow(() -> new IllegalArgumentException("Usuário autenticado não possui cadastro de Docente"));
        oportunidade.setStatus(StatusOportunidade.APROVADA);
        oportunidade.setDocenteValidador(docente);
        return oportunidadeRepo.save(oportunidade);
    }

    public Oportunidade reprovar(Long id, String motivo) {
        if (motivo == null || motivo.isBlank()) {
            throw new IllegalArgumentException("Reprovação exige um motivo");
        }
        var usuario = authService.exigirPapel(Papel.DOCENTE);
        var oportunidade = buscarOuFalhar(id);
        exigirStatusEntre(oportunidade, StatusOportunidade.AGUARDANDO_APROVACAO);
        var docente = docenteRepo.findByUsuario(usuario)
                .orElseThrow(() -> new IllegalArgumentException("Usuário autenticado não possui cadastro de Docente"));
        oportunidade.setStatus(StatusOportunidade.REPROVADA);
        oportunidade.setDocenteValidador(docente);
        return oportunidadeRepo.save(oportunidade);
    }

    public Oportunidade corrigirEReenviar(Long id, Oportunidade dadosAtualizados) {
        var usuario = authService.exigirPapel(Papel.DISCENTE_DIRETOR);
        var oportunidade = buscarOuFalhar(id);
        exigirResponsavel(oportunidade, usuario.getId());
        exigirStatusEntre(oportunidade, StatusOportunidade.REPROVADA);
        oportunidade.setTitulo(dadosAtualizados.getTitulo());
        oportunidade.setDescricao(dadosAtualizados.getDescricao());
        oportunidade.setTipo(dadosAtualizados.getTipo());
        oportunidade.setModalidade(dadosAtualizados.getModalidade());
        oportunidade.setCargaHoraria(dadosAtualizados.getCargaHoraria());
        oportunidade.setVagas(dadosAtualizados.getVagas());
        oportunidade.setDataInicio(dadosAtualizados.getDataInicio());
        oportunidade.setDataFim(dadosAtualizados.getDataFim());
        oportunidade.setDataInicioInscricoes(dadosAtualizados.getDataInicioInscricoes());
        oportunidade.setDataFimInscricoes(dadosAtualizados.getDataFimInscricoes());
        oportunidade.setStatus(StatusOportunidade.AGUARDANDO_APROVACAO);
        return oportunidadeRepo.save(oportunidade);
    }

    public Oportunidade abrirInscricoes(Long id) {
        authService.exigirPapel(Papel.DISCENTE_DIRETOR, Papel.DOCENTE);
        var oportunidade = buscarOuFalhar(id);
        exigirStatusEntre(oportunidade, StatusOportunidade.APROVADA);
        oportunidade.setStatus(StatusOportunidade.ABERTA);
        return oportunidadeRepo.save(oportunidade);
    }

    public Oportunidade encerrar(Long id) {
        authService.exigirPapel(Papel.DISCENTE_DIRETOR, Papel.DOCENTE);
        var oportunidade = buscarOuFalhar(id);
        exigirStatusEntre(oportunidade, StatusOportunidade.APROVADA, StatusOportunidade.ABERTA);
        oportunidade.setStatus(StatusOportunidade.ENCERRADA);
        return oportunidadeRepo.save(oportunidade);
    }

    public Oportunidade cancelar(Long id) {
        var usuario = authService.exigirPapel(Papel.DISCENTE_DIRETOR);
        var oportunidade = buscarOuFalhar(id);
        exigirResponsavel(oportunidade, usuario.getId());
        if (oportunidade.getStatus() == StatusOportunidade.ENCERRADA
                || oportunidade.getStatus() == StatusOportunidade.CANCELADA) {
            throw new IllegalArgumentException("Oportunidade não pode ser cancelada no status atual: " + oportunidade.getStatus());
        }
        oportunidade.setStatus(StatusOportunidade.CANCELADA);
        return oportunidadeRepo.save(oportunidade);
    }

    private Oportunidade buscarOuFalhar(Long id) {
        return oportunidadeRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Oportunidade não encontrada"));
    }

    private void exigirResponsavel(Oportunidade oportunidade, Long usuarioId) {
        if (!usuarioId.equals(oportunidade.getResponsavelId())) {
            throw new IllegalArgumentException("Você só pode operar oportunidades que criou");
        }
    }

    private void exigirStatusEntre(Oportunidade oportunidade, StatusOportunidade... permitidos) {
        for (var status : permitidos) {
            if (oportunidade.getStatus() == status) {
                return;
            }
        }
        throw new IllegalArgumentException("Operação inválida para o status atual: " + oportunidade.getStatus());
    }
}
