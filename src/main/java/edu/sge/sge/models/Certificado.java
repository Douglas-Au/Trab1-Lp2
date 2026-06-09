package edu.sge.sge.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "certificados")
@Data
public class Certificado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "uuid_hash", unique = true, nullable = false)
    private String uuidHash;

    @ManyToOne
    @JoinColumn(name = "discente_id")
    private Discente discente;

    @ManyToOne
    @JoinColumn(name = "oportunidade_id")
    private Oportunidade oportunidade;

    @ManyToOne
    @JoinColumn(name = "aproveitamento_id")
    private Aproveitamento aproveitamento;

    @Column(name = "data_emissao")
    private LocalDate dataEmissao;

    private int horas;

    @Column(columnDefinition = "TEXT")
    private String motivacao;

    @Column(name = "status_assinatura")
    private String statusAssinatura;

    @Column(name = "qr_code_path")
    private String qrCodePath;

    @Column(name = "certificado_path")
    private String certificadoPath;
}
