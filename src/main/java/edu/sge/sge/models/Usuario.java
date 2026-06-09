package edu.sge.sge.models;

import edu.sge.sge.enums.Papel;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "usuarios")
@Data
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String email;
    private String senha;
    private boolean ativo;

    @Enumerated(EnumType.STRING)
    private Papel papel;
}
