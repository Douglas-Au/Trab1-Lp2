package edu.sge.sge.repository;

import edu.sge.sge.models.GrupoMembro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GrupoMembroRepo extends JpaRepository<GrupoMembro, Long> {
    List<GrupoMembro> findByGrupoId(Long grupoId);
    boolean existsByGrupoIdAndUsuarioIdAndDataSaidaIsNull(Long grupoId, Long usuarioId);
}
