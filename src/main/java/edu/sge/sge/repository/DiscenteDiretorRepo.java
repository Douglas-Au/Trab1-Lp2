package edu.sge.sge.repository;

import edu.sge.sge.models.DiscenteDiretor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiscenteDiretorRepo extends JpaRepository<DiscenteDiretor, Long> {
    List<DiscenteDiretor> findByGrupoId(Long grupoId);
    boolean existsByDiscenteId(Long discenteId);
}
