package edu.sge.sge.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "auditorias")
@Data
public class Auditoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    private String acao;

    @Column(name = "entidade_tipo")
    private String entidadeTipo;

    @Column(name = "entidade_id")
    private Long entidadeId;

    @Column(name = "dados_anteriores", columnDefinition = "TEXT")
    private String dadosAnteriores;

    @Column(name = "dados_novos", columnDefinition = "TEXT")
    private String dadosNovos;

    @Column(name = "ip_address")
    private String ipAddress;

    @Column(name = "user_agent", columnDefinition = "TEXT")
    private String userAgent;
}
