package edu.sge.sge.services;

import edu.sge.sge.models.Usuario;
import edu.sge.sge.repository.UsuarioRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepo usuarioRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<Usuario> getAll() {
        return usuarioRepo.findAll();
    }

    public Usuario createUser(Usuario usuario) {
        if(usuarioRepo.findByEmail(usuario.getEmail()).isPresent()) {
            throw new RuntimeException("Usuario existente");

        }
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        return usuarioRepo.save(usuario);
    }

    public Optional<Usuario> getUserById(Long id) {
        return usuarioRepo.findById(id);
    }

    public Usuario updateUser(Long id, Usuario novoUsuario) {
        var existente = usuarioRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));
        novoUsuario.setId(id);
        if (novoUsuario.getSenha() == null || novoUsuario.getSenha().isBlank()) {
            novoUsuario.setSenha(existente.getSenha());
        } else {
            novoUsuario.setSenha(passwordEncoder.encode(novoUsuario.getSenha()));
        }
        return usuarioRepo.save(novoUsuario);
    }

    public void deleteUser(Long id) {
        usuarioRepo.deleteById(id);
    }

    public void deactivateUser(Long id) {
        var usuario = usuarioRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));
        usuario.setAtivo(false);
        usuarioRepo.save(usuario);
    }
}
