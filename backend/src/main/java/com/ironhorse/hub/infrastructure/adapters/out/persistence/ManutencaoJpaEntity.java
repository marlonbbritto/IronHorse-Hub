package com.ironhorse.hub.infrastructure.adapters.out.persistence;

import com.ironhorse.hub.domain.TipoServico;
import jakarta.persistence.*;
import java.time.LocalDate;

/**
 * Entidade JPA para mapeamento com o banco de dados PostgreSQL.
 */
@Entity
@Table(name = "manutencoes")
public class ManutencaoJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "moto_id", nullable = false)
    private Long motoId;

    @Column(nullable = false)
    private String descritivo;

    @Column(nullable = false)
    private Double km;

    @Column(name = "data_servico", nullable = false)
    private LocalDate dataServico;

    private Double custo;

    private String prestador;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_servico", nullable = false)
    private TipoServico tipoServico;

    public ManutencaoJpaEntity() {}

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getMotoId() { return motoId; }
    public void setMotoId(Long motoId) { this.motoId = motoId; }
    public String getDescritivo() { return descritivo; }
    public void setDescritivo(String descritivo) { this.descritivo = descritivo; }
    public Double getKm() { return km; }
    public void setKm(Double km) { this.km = km; }
    public LocalDate getDataServico() { return dataServico; }
    public void setDataServico(LocalDate dataServico) { this.dataServico = dataServico; }
    public Double getCusto() { return custo; }
    public void setCusto(Double custo) { this.custo = custo; }
    public String getPrestador() { return prestador; }
    public void setPrestador(String prestador) { this.prestador = prestador; }
    public TipoServico getTipoServico() { return tipoServico; }
    public void setTipoServico(TipoServico tipoServico) { this.tipoServico = tipoServico; }
}
