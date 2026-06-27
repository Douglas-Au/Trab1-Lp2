package edu.sge.sge.repository;

import edu.sge.sge.models.Aproveitamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AproveitamentoRepo extends JpaRepository<Aproveitamento, Long> {

    List<Aproveitamento> findByDiscenteId(Long discenteId);
}
