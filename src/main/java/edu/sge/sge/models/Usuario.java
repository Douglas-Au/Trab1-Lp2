package edu.sge.sge.models;

import com.fasterxml.jackson.annotation.JsonProperty;
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

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String senha;

    private boolean ativo;

    @Enumerated(EnumType.STRING)
    private Papel papel;
}
