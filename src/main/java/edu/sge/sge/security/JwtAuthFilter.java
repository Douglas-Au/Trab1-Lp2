package edu.sge.sge.security;

import edu.sge.sge.repository.UsuarioRepo;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UsuarioRepo usuarioRepo;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        var header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            try {
                var usuarioId = jwtUtil.extrairUsuarioId(header.substring(7));
                usuarioRepo.findById(usuarioId).ifPresent(usuario -> {
                    var authority = new SimpleGrantedAuthority("ROLE_" + usuario.getPapel().name());
                    var auth = new UsernamePasswordAuthenticationToken(usuario, null, List.of(authority));
                    SecurityContextHolder.getContext().setAuthentication(auth);
                });
            } catch (JwtException | NumberFormatException ignored) {
                // token inválido ou expirado: segue sem autenticar; rotas protegidas vão rejeitar depois
            }
        }
        filterChain.doFilter(request, response);
    }
}
