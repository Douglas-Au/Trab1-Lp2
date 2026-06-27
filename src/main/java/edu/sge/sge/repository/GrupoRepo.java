package edu.sge.sge.repository;

import edu.sge.sge.models.Grupo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GrupoRepo extends JpaRepository<Grupo, Long> {
}
