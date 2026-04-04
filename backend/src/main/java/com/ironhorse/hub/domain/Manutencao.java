package com.ironhorse.hub.domain;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Entidade de Domínio representando um registro de Manutenção.
 * Segue os princípios da Arquitetura Hexagonal sendo um POJO puro.
 */
public class Manutencao {
    private Long id;
    private Long motoId;
    private String descritivo;
    private Double km;
    private LocalDate dataServico;
    private Double custo;
    private String prestador;
    private TipoServico tipoServico;

    public Manutencao() {}

    public Manutencao(Long id, Long motoId, String descritivo, Double km, LocalDate dataServico, Double custo, String prestador, TipoServico tipoServico) {
        validate(descritivo, km, dataServico);
        this.id = id;
        this.motoId = motoId;
        this.descritivo = descritivo;
        this.km = km;
        this.dataServico = dataServico;
        this.custo = custo;
        this.prestador = prestador;
        this.tipoServico = tipoServico;
    }

    private void validate(String descritivo, Double km, LocalDate dataServico) {
        if (descritivo == null || descritivo.isBlank()) {
            throw new IllegalArgumentException("Descrição é obrigatória");
        }
        if (km == null || km < 0) {
            throw new IllegalArgumentException("Quilometragem deve ser positiva");
        }
        if (dataServico == null || dataServico.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Data do serviço não pode ser futura");
        }
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Manutencao that = (Manutencao) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
