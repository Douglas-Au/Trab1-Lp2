package Services;

import Entidades.Usuario;

import java.util.ArrayList;
import java.util.List;

public class UsuarioService {
    private List<Usuario> usuarios = new ArrayList<>();

    public Usuario login(String email, String senha){
        Usuario resultado = null;

        for(Usuario u: usuarios){
            if(u.getEmail().equals(email) && u.getSenha().equals(senha)){
                System.out.println("Usuario loggado");
                return u;
            }
        }
        System.out.println("Usuario não encontrado");
        return null;
    }

    public Usuario cadastrar(Usuario usuario){

        //TODO checar se o usuario já tem cadastro
        usuarios.add(usuario);

        return null;
    }

    public void recuperarSenha(String email){
        for(Usuario u: usuarios){
            if(u.getEmail().equals(email)){
                System.out.println(u.getSenha());
                return;
            }
        }
    }

    public void autocadastrar() {
        //TODO
    }

    @Override
    public String toString() {
        return "UsuarioService{" +
                "usuarios=" + usuarios +
                '}';
    }
}
