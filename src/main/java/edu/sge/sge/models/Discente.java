package edu.sge.sge.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "discentes")
@Data
public class Discente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "curso_id")
    private Curso curso;

    private String matricula;

    @Column(name = "semestre_atual")
    private int semestreAtual;
}
