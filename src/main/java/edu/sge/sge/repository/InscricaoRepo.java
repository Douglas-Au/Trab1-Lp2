package edu.sge.sge.repository;

import edu.sge.sge.enums.StatusInscricao;
import edu.sge.sge.models.Inscricao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InscricaoRepo extends JpaRepository<Inscricao, Long> {
    List<Inscricao> findByOportunidadeId(Long oportunidadeId);
    List<Inscricao> findByDiscenteId(Long discenteId);
    long countByOportunidadeIdAndStatus(Long oportunidadeId, StatusInscricao status);
    boolean existsByOportunidadeIdAndDiscenteIdAndStatusNot(Long oportunidadeId, Long discenteId, StatusInscricao status);
}
