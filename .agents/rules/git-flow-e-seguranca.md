---
trigger: always_on
glob: "*"
description: "Governança de Código e Segurança"
---
# Segurança e Fluxo de Trabalho
- Commits devem ser atômicos (focados em uma única mudança) e seguir o padrão Conventional Commits.
- Antes de cada commit, o @sec deve validar se não há chaves de API, tokens ou credenciais no código.
- É obrigatório validar o .gitignore para garantir que pastas de planejamento técnico e arquivos sensíveis não sejam indexados.
- Realize merges utilizando a flag --no-ff para preservar o histórico de branches.
