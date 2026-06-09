package edu.sge.sge.models;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Table(name = "ppcs_historico")
@Data
public class PPC_Historico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "curso_id")
    private Curso curso;

    private String versao;

    @Column(name = "carga_horaria_adesmas")
    private String cargaHorariaAdesmas;

    @Column(name = "data_vigencia_inicio")
    private LocalDate dataVigenciaInicio;

    @Column(name = "data_vigencia_fim")
    private LocalDate dataVigenciaFim;

    @ManyToOne
    @JoinColumn(name = "autor_alteracoes_id")
    private Usuario autorAlteracoes;
}
