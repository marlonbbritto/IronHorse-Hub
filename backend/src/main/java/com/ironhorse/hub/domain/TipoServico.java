package com.ironhorse.hub.domain;

/**
 * Tipos de serviços de manutenção disponíveis para registro.
 */
public enum TipoServico {
    OLEO("Troca de Óleo"),
    PNEUS("Pneus"),
    REVISAO("Revisão Geral"),
    PECAS("Substituição de Peças"),
    OUTROS("Outros Serviços");

    private final String descricao;

    TipoServico(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
