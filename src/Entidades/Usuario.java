package Entidades;
import Enums.*;

import java.util.*;

public class Usuario {
    private String nome;
    private String email;
    private String senha;
    private Papel papel;
    private Boolean ativo;


    public Usuario(String nome, String email, String senha, Papel papel, Boolean ativo) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.ativo = ativo;
        this.papel = papel;
    }

    public List<Oportunidade> obterOportunidades(){
        //TODO
        return null;
    }

    public void mudarSenha(String novaSenha){
        senha = novaSenha;
    }

    //Getters an Setters
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public Papel getPapel() {
        return papel;
    }

    public void setPapel(Papel papel) {
        this.papel = papel;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }
}
