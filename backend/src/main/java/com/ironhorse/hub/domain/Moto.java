package com.ironhorse.hub.domain;

import java.util.Objects;

/**
 * Entidade de Domínio representando uma Moto.
 * Segue os princípios da Arquitetura Hexagonal sendo uma classe POJO pura.
 */
public class Moto {
    private Long id;
    private String modelo;
    private Integer ano;
    private Double km;
    private String placa;
    private String vin;
    private String ownerEmail;
    private java.time.LocalDate dataUltimaAtualizacao;

    public Moto() {}

    public Moto(Long id, String modelo, Integer ano, Double km, String placa, String vin, String ownerEmail, java.time.LocalDate dataUltimaAtualizacao) {
        validate(modelo, ano, km);
        this.id = id;
        this.modelo = modelo;
        this.ano = ano;
        this.km = km;
        this.placa = placa;
        this.vin = vin;
        this.ownerEmail = ownerEmail;
        this.dataUltimaAtualizacao = dataUltimaAtualizacao;
    }

    public Moto(Long id, String modelo, Integer ano, Double km, String placa, String vin, String ownerEmail) {
        this(id, modelo, ano, km, placa, vin, ownerEmail, null);
    }

    private void validate(String modelo, Integer ano, Double km) {
        if (modelo == null || modelo.isBlank()) {
            throw new IllegalArgumentException("Modelo é obrigatório");
        }
        if (ano == null || ano <= 1900) {
            throw new IllegalArgumentException("Ano é obrigatório e deve ser válido");
        }
        if (km == null || km < 0) {
            throw new IllegalArgumentException("KM é obrigatório e não pode ser negativo");
        }
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { 
        validate(modelo, this.ano, this.km);
        this.modelo = modelo; 
    }

    public Integer getAno() { return ano; }
    public void setAno(Integer ano) { 
        validate(this.modelo, ano, this.km);
        this.ano = ano; 
    }

    public Double getKm() { return km; }
    public void setKm(Double km) { 
        validate(this.modelo, this.ano, km);
        this.km = km; 
    }

    public void atualizarKm(Double novaKm) {
        if (novaKm == null || novaKm < this.km) {
            throw new IllegalArgumentException("A nova quilometragem não pode ser inferior à atual (" + this.km + " km)");
        }
        this.km = novaKm;
        this.dataUltimaAtualizacao = java.time.LocalDate.now();
    }

    public java.time.LocalDate getDataUltimaAtualizacao() { return dataUltimaAtualizacao; }
    public void setDataUltimaAtualizacao(java.time.LocalDate dataUltimaAtualizacao) { this.dataUltimaAtualizacao = dataUltimaAtualizacao; }

    public String getPlaca() { return placa; }
    public void setPlaca(String placa) { this.placa = placa; }

    public String getVin() { return vin; }
    public void setVin(String vin) { this.vin = vin; }

    public String getOwnerEmail() { return ownerEmail; }
    public void setOwnerEmail(String ownerEmail) { this.ownerEmail = ownerEmail; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Moto moto = (Moto) o;
        return Objects.equals(id, moto.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
