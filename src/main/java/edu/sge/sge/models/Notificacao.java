package edu.sge.sge.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "notificacoes")
@Data
public class Notificacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    private String tipo;
    private String titulo;

    @Column(columnDefinition = "TEXT")
    private String mensagem;

    private boolean lida;

    @Column(name = "lida_em")
    private LocalDateTime lidaEm;

    @Column(name = "url_referencia")
    private String urlReferencia;
}
