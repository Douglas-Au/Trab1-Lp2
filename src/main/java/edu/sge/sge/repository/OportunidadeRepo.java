package edu.sge.sge.repository;

import edu.sge.sge.enums.StatusOportunidade;
import edu.sge.sge.models.Oportunidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OportunidadeRepo extends JpaRepository<Oportunidade, Long> {
    List<Oportunidade> findByStatus(StatusOportunidade status);
}
