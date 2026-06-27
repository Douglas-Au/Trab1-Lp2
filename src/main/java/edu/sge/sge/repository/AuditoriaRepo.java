package edu.sge.sge.repository;

import edu.sge.sge.models.Auditoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuditoriaRepo extends JpaRepository<Auditoria, Long> {
    List<Auditoria> findByUsuarioId(Long usuarioId);
}
