package com.ironhorse.hub.infrastructure.adapters.out.persistence;

import com.ironhorse.hub.domain.TipoServico;
import jakarta.persistence.*;

/**
 * Entidade JPA para mapeamento da configuração de alertas no banco de dados.
 */
@Entity
@Table(name = "config_alertas", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"moto_id", "tipo_servico"})
})
public class AlertaConfigJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "moto_id", nullable = false)
    private Long motoId;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_servico", nullable = false)
    private TipoServico tipoServico;

    @Column(name = "periodicidade_km", nullable = false)
    private Double periodicidadeKm;

    @Column(nullable = false)
    private boolean ativo = true;

    public AlertaConfigJpaEntity() {}

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getMotoId() { return motoId; }
    public void setMotoId(Long motoId) { this.motoId = motoId; }
    public TipoServico getTipoServico() { return tipoServico; }
    public void setTipoServico(TipoServico tipoServico) { this.tipoServico = tipoServico; }
    public Double getPeriodicidadeKm() { return periodicidadeKm; }
    public void setPeriodicidadeKm(Double periodicidadeKm) { this.periodicidadeKm = periodicidadeKm; }
    public boolean isAtivo() { return ativo; }
    public void setAtivo(boolean ativo) { this.ativo = ativo; }
}
