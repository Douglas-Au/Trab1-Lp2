package edu.sge.sge.repository;

import edu.sge.sge.models.Discente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DiscenteRepo extends JpaRepository<Discente, Long> {

    Optional<Discente> findByMatricula(String matricula);
}
