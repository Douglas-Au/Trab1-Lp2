package edu.sge.sge.repository;

import edu.sge.sge.models.Certificado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CertificadoRepo extends JpaRepository<Certificado, Long> {
    Optional<Certificado> findByUuidHash(String uuidHash);
    List<Certificado> findByDiscenteId(Long discenteId);
}
