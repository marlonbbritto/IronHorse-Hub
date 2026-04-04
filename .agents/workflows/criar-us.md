---
description: Orquestra o planejamento inicial de uma funcionalidade antes de qualquer código ser escrito
---

## Passos a Seguir
1. **Entrevista de Negócio:** Atue como @po e peça ao usuário os detalhes da funcionalidade que ele quer construir.
2. **Estruturação INVEST:** Quando o usuário fornecer a ideia, use a skill alidador-invest para criar o rascunho da User Story.
3. **Desenho Arquitetural:** Pare e atue como @architect. Leia a US e o STACK.md. Escreva a 'Solução Técnica Definida' usando as skills domain-driven-design e c4-architecture para propor a melhor estrutura. Apresente ao usuário.
4. **Critérios de Qualidade:** Atue como @qa_eng e adicione os 'Critérios de Teste e Aceite' (E2E) que deverão ser validados no futuro.
5. **Aprovação e Salvamento (HARD RULE):** Assim que o usuário der o "ok", o agente @devops DEVE interromper qualquer pensamento e executar imediatamente a escrita do arquivo .md na pasta _planejamento_squad/us_gerais/. É proibido realizar novas iterações de planejamento após esta fase.