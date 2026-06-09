package edu.sge.sge.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "planos_atividade")
@Data
public class PlanoAtividade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "oportunidade_id")
    private Oportunidade oportunidade;

    @Column(columnDefinition = "TEXT")
    private String objetivos;

    @Column(columnDefinition = "TEXT")
    private String metodologia;

    @Column(columnDefinition = "TEXT")
    private String cronograma;

    @Column(name = "relatorio_final", columnDefinition = "TEXT")
    private String relatorioFinal;
}
