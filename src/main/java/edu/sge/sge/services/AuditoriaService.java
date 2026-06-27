package edu.sge.sge.services;

import edu.sge.sge.models.Auditoria;
import edu.sge.sge.models.Usuario;
import edu.sge.sge.repository.AuditoriaRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuditoriaService {

    @Autowired
    private AuditoriaRepo auditoriaRepo;

    public List<Auditoria> getAll() {
        return auditoriaRepo.findAll();
    }

    public Optional<Auditoria> getById(Long id) {
        return auditoriaRepo.findById(id);
    }

    public List<Auditoria> getByUsuario(Long usuarioId) {
        return auditoriaRepo.findByUsuarioId(usuarioId);
    }

    /**
     * Registra uma ação de auditoria. Uso interno por outros serviços —
     * não exposto como endpoint de criação público.
     */
    public Auditoria registrar(Usuario usuario, String acao, String entidadeTipo, Long entidadeId,
                               String dadosAnteriores, String dadosNovos, String ipAddress, String userAgent) {
        var auditoria = new Auditoria();
        auditoria.setUsuario(usuario);
        auditoria.setAcao(acao);
        auditoria.setEntidadeTipo(entidadeTipo);
        auditoria.setEntidadeId(entidadeId);
        auditoria.setDadosAnteriores(dadosAnteriores);
        auditoria.setDadosNovos(dadosNovos);
        auditoria.setIpAddress(ipAddress);
        auditoria.setUserAgent(userAgent);
        return auditoriaRepo.save(auditoria);
    }
}
