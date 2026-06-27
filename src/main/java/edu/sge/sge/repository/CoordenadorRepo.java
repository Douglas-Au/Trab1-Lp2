package edu.sge.sge.repository;


import edu.sge.sge.models.CoordenadorUCE;
import edu.sge.sge.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CoordenadorRepo extends JpaRepository<CoordenadorUCE, Long> {
    boolean existsByUsuario(Usuario usuario);
}
