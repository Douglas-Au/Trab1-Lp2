package edu.sge.sge.models;

import edu.sge.sge.enums.StatusAproveitamento;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "aproveitamentos")
@Data
public class Aproveitamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "discente_id")
    private Discente discente;

    @Column(columnDefinition = "TEXT")
    private String descricao;

    private String instituicao;
    private int horas;

    @Enumerated(EnumType.STRING)
    private StatusAproveitamento status;

    @Column(name = "certificado_path")
    private String certificadoPath;

    @ManyToOne
    @JoinColumn(name = "avaliador_id")
    private Usuario avaliador;

    @Column(name = "motivo_rejeicao", columnDefinition = "TEXT")
    private String motivoRejeicao;

    @Column(name = "data_atividade_inicio")
    private LocalDate dataAtividadeInicio;

    @Column(name = "data_atividade_fim")
    private LocalDate dataAtividadeFim;

    @Column(name = "prazo_reenvio")
    private LocalDate prazoReenvio;
}
