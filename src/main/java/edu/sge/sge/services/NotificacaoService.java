package edu.sge.sge.services;

import edu.sge.sge.models.Notificacao;
import edu.sge.sge.repository.NotificacaoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class NotificacaoService {

    @Autowired
    private NotificacaoRepo notificacaoRepo;

    public List<Notificacao> getAll() {
        return notificacaoRepo.findAll();
    }

    public Optional<Notificacao> getById(Long id) {
        return notificacaoRepo.findById(id);
    }

    public List<Notificacao> getByUsuario(Long usuarioId) {
        return notificacaoRepo.findByUsuarioId(usuarioId);
    }

    public List<Notificacao> getNaoLidas(Long usuarioId) {
        return notificacaoRepo.findByUsuarioIdAndLidaFalse(usuarioId);
    }

    public Notificacao create(Notificacao notificacao) {
        notificacao.setLida(false);
        notificacao.setLidaEm(null);
        return notificacaoRepo.save(notificacao);
    }

    public Notificacao marcarComoLida(Long id) {
        var notificacao = notificacaoRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Notificação não encontrada"));
        notificacao.setLida(true);
        notificacao.setLidaEm(LocalDateTime.now());
        return notificacaoRepo.save(notificacao);
    }

    public void delete(Long id) {
        notificacaoRepo.deleteById(id);
    }
}
