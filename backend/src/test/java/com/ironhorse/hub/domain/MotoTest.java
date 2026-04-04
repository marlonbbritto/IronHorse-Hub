package com.ironhorse.hub.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MotoTest {

    @Test
    void shouldCreateAMotoCorrectly() {
        Moto moto = new Moto(1L, "Fat Boy", 2023, 1500.0, "ABC-1234", "VIN123456789", "owner@test.com");
        
        assertEquals(1L, moto.getId());
        assertEquals("Fat Boy", moto.getModelo());
        assertEquals(2023, moto.getAno());
        assertEquals(1500.0, moto.getKm());
        assertEquals("ABC-1234", moto.getPlaca());
        assertEquals("VIN123456789", moto.getVin());
        assertEquals("owner@test.com", moto.getOwnerEmail());
    }

    @Test
    void shouldThrowExceptionWhenModeloIsBlank() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Moto(1L, "", 2023, 1500.0, null, null, "owner@test.com");
        });
        assertEquals("Modelo é obrigatório", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenAnoIsInvalid() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Moto(1L, "Softail", 1899, 0.0, null, null, "owner@test.com");
        });
        assertEquals("Ano é obrigatório e deve ser válido", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenKmIsNegative() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Moto(1L, "Street Glide", 2024, -1.0, null, null, "owner@test.com");
        });
        assertEquals("KM é obrigatório e não pode ser negativo", exception.getMessage());
    }
}
