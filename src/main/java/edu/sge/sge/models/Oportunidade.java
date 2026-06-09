package edu.sge.sge.models;

import edu.sge.sge.enums.ModalidadeOportunidade;
import edu.sge.sge.enums.StatusOportunidade;
import edu.sge.sge.enums.TipoOportunidade;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "oportunidades")
@Data
public class Oportunidade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    @Column(columnDefinition = "TEXT")
    private String descricao;

    @Enumerated(EnumType.STRING)
    private TipoOportunidade tipo;

    @Enumerated(EnumType.STRING)
    private ModalidadeOportunidade modalidade;

    @Column(name = "carga_horaria")
    private int cargaHoraria;

    private int vagas;

    @Enumerated(EnumType.STRING)
    private StatusOportunidade status;

    @Column(name = "data_inicio")
    private LocalDate dataInicio;

    @Column(name = "data_fim")
    private LocalDate dataFim;

    @Column(name = "data_inicio_inscricoes")
    private LocalDate dataInicioInscricoes;

    @Column(name = "data_fim_inscricoes")
    private LocalDate dataFimInscricoes;

    @Column(name = "responsavel_tipo")
    private String responsavelTipo;

    @Column(name = "responsavel_id")
    private Long responsavelId;

    @ManyToOne
    @JoinColumn(name = "docente_validador_id")
    private Docente docenteValidador;
}
