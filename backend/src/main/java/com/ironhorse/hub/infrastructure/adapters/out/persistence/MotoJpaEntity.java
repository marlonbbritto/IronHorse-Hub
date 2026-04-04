package com.ironhorse.hub.infrastructure.adapters.out.persistence;

import jakarta.persistence.*;

/**
 * Entidade JPA para mapeamento com o banco de dados PostgreSQL.
 * Localizada na camada de Infrastructure.
 */
@Entity
@Table(name = "motos")
public class MotoJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String modelo;

    @Column(nullable = false)
    private Integer ano;

    @Column(nullable = false)
    private Double km;

    private String placa;

    private String vin;

    @Column(name = "owner_email", nullable = false)
    private String ownerEmail;

    @Column(name = "data_ultima_atualizacao")
    private java.time.LocalDate dataUltimaAtualizacao;

    public MotoJpaEntity() {}

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }
    public Integer getAno() { return ano; }
    public void setAno(Integer ano) { this.ano = ano; }
    public Double getKm() { return km; }
    public void setKm(Double km) { this.km = km; }
    public String getPlaca() { return placa; }
    public void setPlaca(String placa) { this.placa = placa; }
    public String getVin() { return vin; }
    public void setVin(String vin) { this.vin = vin; }
    public String getOwnerEmail() { return ownerEmail; }
    public void setOwnerEmail(String ownerEmail) { this.ownerEmail = ownerEmail; }
    public java.time.LocalDate getDataUltimaAtualizacao() { return dataUltimaAtualizacao; }
    public void setDataUltimaAtualizacao(java.time.LocalDate dataUltimaAtualizacao) { this.dataUltimaAtualizacao = dataUltimaAtualizacao; }
}
