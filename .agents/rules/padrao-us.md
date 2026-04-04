---
trigger: always_on
---

# Padrão de Documentação: User Story

Toda User Story (US) neste projeto deve seguir rigorosamente as seções abaixo:

## 1. Descrição de Negócio
- **Como** [persona]
- **Quero** [funcionalidade]
- **Para que** [valor de negócio]

## 2. Validação INVEST
- Análise de Independência, Negociável, Valiosa, Estimável, Small (Pequena) e Testável.

## 3. Critérios de Teste e Aceite (QA)
- Tabela de campos (Tipo, Obrigatório).
- Cenários de Sucesso e Erro (Gherkin preferencialmente).

## 4. Solução Técnica (Arquitetura Hexagonal)
- **Domain Layer**: Entidades e regras puras.
- **Application Layer**: Use Cases e Portas (Interfaces).
- **Infrastructure Layer**: Adapters (Controllers, Repositories JPA, Clientes API).

---
*Nota: US que não seguirem este padrão devem ser rejeitadas pelo @sec ou @agile.*