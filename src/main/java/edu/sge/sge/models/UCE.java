package edu.sge.sge.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "uces")
@Data
public class UCE {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ppc_id")
    private PPC_Historico ppc;

    private String nome;
    private String codigo;

    @Column(name = "carga_horaria")
    private int cargaHoraria;

    @Column(name = "semestre_sugerido")
    private int semestreSugerido;
}
