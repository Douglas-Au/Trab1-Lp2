package edu.sge.sge.repository;

import edu.sge.sge.models.Notificacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificacaoRepo extends JpaRepository<Notificacao, Long> {
    List<Notificacao> findByUsuarioId(Long usuarioId);
    List<Notificacao> findByUsuarioIdAndLidaFalse(Long usuarioId);
}
