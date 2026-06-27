package edu.sge.sge.repository;

import edu.sge.sge.models.AproveitamentoHistorico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AproveitamentoHistoricoRepo extends JpaRepository<AproveitamentoHistorico, Long> {

    List<AproveitamentoHistorico> findByAproveitamentoIdOrderByIdAsc(Long aproveitamentoId);
}
