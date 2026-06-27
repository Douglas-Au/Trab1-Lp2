package edu.sge.sge.repository;

import edu.sge.sge.models.Comunicado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComunicadoRepo extends JpaRepository<Comunicado, Long> {
    List<Comunicado> findByCursoId(Long cursoId);
}
