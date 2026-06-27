package edu.sge.sge.security;

import edu.sge.sge.models.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${sge.jwt.secret}")
    private String secret;

    @Value("${sge.jwt.expiration-ms}")
    private long expirationMs;

    private SecretKey getKey() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public String gerarToken(Usuario usuario) {
        var agora = new Date();
        return Jwts.builder()
                .subject(usuario.getId().toString())
                .claim("email", usuario.getEmail())
                .claim("papel", usuario.getPapel().name())
                .issuedAt(agora)
                .expiration(new Date(agora.getTime() + expirationMs))
                .signWith(getKey())
                .compact();
    }

    public Long extrairUsuarioId(String token) {
        return Long.parseLong(extrairClaims(token).getSubject());
    }

    private Claims extrairClaims(String token) {
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
