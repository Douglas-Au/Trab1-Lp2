package edu.sge.sge.repository;

import edu.sge.sge.models.UCE;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UceRepo extends JpaRepository<UCE, Long> {
    List<UCE> findByPpcId(Long ppcId);
}
