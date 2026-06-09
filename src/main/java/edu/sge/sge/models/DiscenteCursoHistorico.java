package edu.sge.sge.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "discentes_curso_historico")
@Data
public class DiscenteCursoHistorico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "discente_id")
    private Discente discente;

    @ManyToOne
    @JoinColumn(name = "curso_anterior_id")
    private Curso cursoAnterior;

    @ManyToOne
    @JoinColumn(name = "curso_novo_id")
    private Curso cursoNovo;

    @Column(name = "data_mudanca")
    private LocalDate dataMudanca;
}
