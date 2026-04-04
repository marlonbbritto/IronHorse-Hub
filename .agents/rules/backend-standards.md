---
trigger: always_on
glob: "src/main/java/**/*"
description: "Padrões de Engenharia para Backend"
---
# Padrões de Desenvolvimento Backend
- Aplique rigorosamente os princípios SOLID e Clean Code.
- Utilize a Arquitetura Hexagonal conforme definido no STACK.md, mantendo o domínio isolado de frameworks.
- Implemente tratamento de exceções global e padronizado.
- Use DTOs para comunicação externa; nunca exponha entidades de banco de dados diretamente na API.
- Garanta que toda regra de negócio esteja coberta por testes unitários.
