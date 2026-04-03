---
name: validador-invest
description: Valida, estrutura e formata User Stories (US) de acordo com o framework INVEST. Apenas use esta habilidade sempre que o usuário pedir para criar, detalhar ou revisar uma US de negócio, garantindo a presença da Solução Técnica e dos Critérios de Teste.
---

## Objetivo
Garantir que toda User Story (US) gerada ou analisada pelo squad atenda rigorosamente ao padrão INVEST e contenha as seções obrigatórias exigidas pela arquitetura e QA.

## Instruções Passo a Passo
1. **Auditoria INVEST:** Avalie o texto da US e garanta que ela seja: Independent, Negotiable, Valuable, Estimable, Small e Testable.
2. **Revisão Técnica:** Confirme se a US possui a seção '### Solução Técnica Definida'. Se não, acione o @architect.
3. **Revisão de QA:** Confirme se a US possui a seção '### Critérios de Teste e Aceite'. Se não, acione o @qa_eng.
4. **Formatação Final:** Formate em Markdown e salve em Português do Brasil.

## Restrições (Constraints)
- NÃO escreva código de software durante o uso desta skill.
- NÃO aprove a US se faltarem os Critérios de Aceite ou a Solução Técnica.
- Salve exclusivamente em '_planejamento_squad/us_gerais/' com sufixo de versão.
