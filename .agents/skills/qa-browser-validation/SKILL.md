---
name: qa-browser-validation
description: Aciona o Browser Subagent nativo para abrir o navegador, navegar na aplicação web local e realizar testes manuais E2E simulados na interface para validar os Critérios de Aceite da User Story. Use sempre que uma subtarefa de Frontend ou integração for finalizada.
---

## Objetivo
Atuar como um Analista de QA realizando o teste de interface do usuário via navegação web real.

## Instruções Passo a Passo
1. **Levantar Ambiente:** Verifique se o Java/Spring e Angular estão rodando.
2. **Iniciar Browser Subagent:** Abra a URL local (ex: localhost:4200).
3. **Execução de Teste:** Siga os Critérios de Aceite na interface (cliques, formulários).
4. **Captura de Evidências:** Gere screenshots ou gravações comprovando o comportamento.
5. **Aprovação:** Compare o visual com o esperado.

## Restrições (Constraints)
- O teste DEVE ser feito interagindo com a interface gráfica.
- Se falhar, documente com screenshot do console e devolva ao @engineer.
