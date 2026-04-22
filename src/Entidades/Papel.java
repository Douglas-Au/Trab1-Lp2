package Entidades;

public class Papel {
    public String descricao;
    public Usuario usuario; //Talvez saia

    public Papel(String descricao) {
        this.descricao = descricao;
    }

    //Getters and setters
    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
