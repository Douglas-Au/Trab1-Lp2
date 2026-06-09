package edu.sge.sge.repository;

import edu.sge.sge.models.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CursoRepo extends JpaRepository<Curso, Long> {

    Optional<Curso> findByCodigo(String codigo);
}
