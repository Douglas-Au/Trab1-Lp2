package edu.sge.sge.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "coordenadores_uce")
@Data
public class CoordenadorUCE {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
}
