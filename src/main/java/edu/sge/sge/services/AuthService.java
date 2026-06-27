package edu.sge.sge.services;

import edu.sge.sge.enums.Papel;
import edu.sge.sge.models.Usuario;
import edu.sge.sge.repository.UsuarioRepo;
import edu.sge.sge.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UsuarioRepo usuarioRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    public String login(String email, String senha) {
        var usuario = usuarioRepo.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("E-mail ou senha inválidos"));
        if (!usuario.isAtivo()) {
            throw new IllegalArgumentException("Usuário desativado");
        }
        if (!passwordEncoder.matches(senha, usuario.getSenha())) {
            throw new IllegalArgumentException("E-mail ou senha inválidos");
        }
        return jwtUtil.gerarToken(usuario);
    }

    public Usuario getUsuarioAutenticado() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !(auth.getPrincipal() instanceof Usuario usuario)) {
            throw new IllegalArgumentException("Usuário não autenticado");
        }
        return usuario;
    }

    public Usuario exigirPapel(Papel... papeisPermitidos) {
        var usuario = getUsuarioAutenticado();
        for (var papel : papeisPermitidos) {
            if (usuario.getPapel() == papel) {
                return usuario;
            }
        }
        throw new IllegalArgumentException("Usuário não tem permissão para esta ação");
    }
}
