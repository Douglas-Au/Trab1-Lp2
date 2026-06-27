package edu.sge.sge.services;

import edu.sge.sge.enums.Papel;
import edu.sge.sge.enums.StatusAproveitamento;
import edu.sge.sge.enums.StatusOportunidade;
import edu.sge.sge.models.Certificado;
import edu.sge.sge.repository.AproveitamentoRepo;
import edu.sge.sge.repository.CertificadoRepo;
import edu.sge.sge.repository.OportunidadeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CertificadoService {

    @Autowired
    private CertificadoRepo certificadoRepo;

    @Autowired
    private AproveitamentoRepo aproveitamentoRepo;

    @Autowired
    private OportunidadeRepo oportunidadeRepo;

    @Autowired
    private AuthService authService;

    public List<Certificado> getAll() {
        return certificadoRepo.findAll();
    }

    public List<Certificado> getByDiscente(Long discenteId) {
        return certificadoRepo.findByDiscenteId(discenteId);
    }

    public Optional<Certificado> validar(String uuidHash) {
        return certificadoRepo.findByUuidHash(uuidHash);
    }

    public Certificado emitir(Certificado certificado) {
        authService.exigirPapel(Papel.COORD_UCE, Papel.COORD_CURSO, Papel.DOCENTE, Papel.ADMIN);

        boolean temOportunidade = certificado.getOportunidade() != null && certificado.getOportunidade().getId() != null;
        boolean temAproveitamento = certificado.getAproveitamento() != null && certificado.getAproveitamento().getId() != null;
        if (temOportunidade == temAproveitamento) {
            throw new IllegalArgumentException("Informe exatamente uma origem: oportunidade OU aproveitamento");
        }
        if (certificado.getDiscente() == null || certificado.getDiscente().getId() == null) {
            throw new IllegalArgumentException("Certificado deve estar vinculado a um discente");
        }

        if (temAproveitamento) {
            var aproveitamento = aproveitamentoRepo.findById(certificado.getAproveitamento().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Aproveitamento não encontrado"));
            if (aproveitamento.getStatus() != StatusAproveitamento.APROVADO) {
                throw new IllegalArgumentException("Certificado só pode ser emitido para Aproveitamento APROVADO");
            }
        } else {
            var oportunidade = oportunidadeRepo.findById(certificado.getOportunidade().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Oportunidade não encontrada"));
            if (oportunidade.getStatus() != StatusOportunidade.ENCERRADA) {
                throw new IllegalArgumentException("Certificado só pode ser emitido para Oportunidade ENCERRADA");
            }
        }

        certificado.setUuidHash(UUID.randomUUID().toString());
        certificado.setDataEmissao(LocalDate.now());
        return certificadoRepo.save(certificado);
    }
}
