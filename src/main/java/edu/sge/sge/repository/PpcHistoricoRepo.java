package edu.sge.sge.repository;

import edu.sge.sge.models.PPC_Historico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PpcHistoricoRepo extends JpaRepository<PPC_Historico, Long> {
    List<PPC_Historico> findByCursoId(Long cursoId);
}
