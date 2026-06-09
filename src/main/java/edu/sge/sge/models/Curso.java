package edu.sge.sge.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "cursos")
@Data
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String codigo;

    @Column(name = "carga_horaria_extensao")
    private int cargaHorariaExtensao;

    @Column(name = "versao_ppc")
    private String versaoPpc;
}
