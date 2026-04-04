package com.ironhorse.hub.domain;

import java.util.Objects;

/**
 * Entidade de Domínio representando a configuração de um Alerta de Manutenção.
 * Permite ao piloto definir metas manuais de periodicidade.
 */
public class AlertaConfig {
    private Long id;
    private Long motoId;
    private TipoServico tipoServico;
    private Double periodicidadeKm;
    private boolean ativo;

    public AlertaConfig() {}

    public AlertaConfig(Long id, Long motoId, TipoServico tipoServico, Double periodicidadeKm, boolean ativo) {
        validate(periodicidadeKm);
        this.id = id;
        this.motoId = motoId;
        this.tipoServico = tipoServico;
        this.periodicidadeKm = periodicidadeKm;
        this.ativo = ativo;
    }

    private void validate(Double periodicidadeKm) {
        if (periodicidadeKm != null && periodicidadeKm <= 0) {
            throw new IllegalArgumentException("A periodicidade deve ser maior que zero KM");
        }
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getMotoId() { return motoId; }
    public void setMotoId(Long motoId) { this.motoId = motoId; }
    public TipoServico getTipoServico() { return tipoServico; }
    public void setTipoServico(TipoServico tipoServico) { this.tipoServico = tipoServico; }
    public Double getPeriodicidadeKm() { return periodicidadeKm; }
    public void setPeriodicidadeKm(Double periodicidadeKm) { 
        validate(periodicidadeKm);
        this.periodicidadeKm = periodicidadeKm; 
    }
    public boolean isAtivo() { return ativo; }
    public void setAtivo(boolean ativo) { this.ativo = ativo; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AlertaConfig that = (AlertaConfig) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
