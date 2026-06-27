package edu.sge.sge.repository;

import edu.sge.sge.models.PlanoAtividade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlanoAtividadeRepo extends JpaRepository<PlanoAtividade, Long> {
    Optional<PlanoAtividade> findByOportunidadeId(Long oportunidadeId);
}
