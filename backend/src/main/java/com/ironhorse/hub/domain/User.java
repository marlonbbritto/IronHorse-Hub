package com.ironhorse.hub.domain;

import java.util.UUID;

/**
 * Entidade de Domínio representando um Usuário.
 * POJO puro seguindo a Arquitetura Hexagonal.
 */
public class User {
    private UUID id;
    private String email;
    private String passwordHash;

    public User() {}

    public User(UUID id, String email, String passwordHash) {
        this.id = id != null ? id : UUID.randomUUID();
        this.email = email;
        this.passwordHash = passwordHash;
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }
}
