package edu.sge.sge.services;

import edu.sge.sge.models.Usuario;
import edu.sge.sge.repository.UsuarioRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepo usuarioRepo;

    public List<Usuario> getAll() {
        return usuarioRepo.findAll();
    }

    public Usuario createUser(Usuario usuario) {
        return usuarioRepo.save(usuario);
    }

    public Optional<Usuario> getUserById(Long id) {
        return usuarioRepo.findById(id);
    }

    public Usuario updateUser(Long id, Usuario novoUsuario) {
        novoUsuario.setId(id);
        return usuarioRepo.save(novoUsuario);
    }

    public void deleteUser(Long id) {
        usuarioRepo.deleteById(id);
    }
}
