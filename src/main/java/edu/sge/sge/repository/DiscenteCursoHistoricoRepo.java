package edu.sge.sge.repository;

import edu.sge.sge.models.DiscenteCursoHistorico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiscenteCursoHistoricoRepo extends JpaRepository<DiscenteCursoHistorico, Long> {
    List<DiscenteCursoHistorico> findByDiscenteId(Long discenteId);
}
