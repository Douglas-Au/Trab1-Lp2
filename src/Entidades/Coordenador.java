package Entidades;

import Enums.Papel;

public class Coordenador extends Usuario {

    public Coordenador(String nome, String email, String senha, Papel papel, Boolean ativo) {
        super(nome, email, senha, papel, ativo);
    }
}
