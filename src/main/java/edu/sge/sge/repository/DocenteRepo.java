package edu.sge.sge.repository;

import edu.sge.sge.models.Docente;
import edu.sge.sge.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DocenteRepo extends JpaRepository<Docente, Long> {

    Optional<Docente> findBySiape(String siape);
    java.util.List<Docente> findByDepartamento(String departamento);
    Optional<Docente> findByUsuario(Usuario usuario);
    boolean existsByUsuario(Usuario usuario);
}
