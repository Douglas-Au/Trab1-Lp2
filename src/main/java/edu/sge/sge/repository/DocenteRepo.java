package edu.sge.sge.repository;

import edu.sge.sge.models.Docente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DocenteRepo extends JpaRepository<Docente, Long> {

    Optional<Docente> findBySiape(String siape);
    java.util.List<Docente> findByDepartamento(String departamento);
}
