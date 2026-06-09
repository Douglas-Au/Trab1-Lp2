package edu.sge.sge.models;

import edu.sge.sge.enums.StatusAproveitamento;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "aproveitamentos_historico")
@Data
public class AproveitamentoHistorico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "aproveitamento_id")
    private Aproveitamento aproveitamento;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_anterior")
    private StatusAproveitamento statusAnterior;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_novo")
    private StatusAproveitamento statusNovo;

    @ManyToOne
    @JoinColumn(name = "avaliador_id")
    private Usuario avaliador;

    @Column(columnDefinition = "TEXT")
    private String observacao;
}
