package edu.sge.sge.models;

import edu.sge.sge.enums.TipoGrupo;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "grupos")
@Data
public class Grupo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @Enumerated(EnumType.STRING)
    private TipoGrupo tipo;

    private String email;

    @Column(columnDefinition = "TEXT")
    private String descricao;

    private String status;

    @ManyToOne
    @JoinColumn(name = "docente_responsavel_id")
    private Docente docenteResponsavel;
}
