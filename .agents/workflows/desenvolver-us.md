# Desenvolver User Story (Execução)
Descrição: Guia o desenvolvimento seguro e atômico de uma User Story validada, passando por codificação, testes automatizados, testes de tela e commit --no-ff.

## Passos a Seguir
1. **Seleção e Quebra de Tarefas:** Solicite ao usuário o nome do arquivo da US localizada em _planejamento_squad/us_gerais/. Atue como @agile para ler o documento e criar uma lista de subtarefas atômicas separando Backend e Frontend. Apresente a lista e **peça autorização** para iniciar.
2. **Desenvolvimento Backend:** Para a subtarefa de backend atual:
   - Atue como @backend_dev para escrever o código utilizando as tecnologias definidas no STACK.md.
   - Escreva e execute os testes unitários e de integração obrigatórios.
   - Pare, atue como @devops para explicar os comandos de terminal necessários e **peça autorização** antes de rodá-los.
3. **Desenvolvimento Frontend:** Para a subtarefa de frontend atual:
   - Atue como @frontend_dev para escrever o código utilizando as tecnologias definidas no STACK.md.
   - Pare, explique comandos de compilação via @devops e **peça autorização** para executá-los no terminal.
4. **Teste de Interface (QA E2E):** Com a aplicação rodando, atue como @qa_eng e ative a habilidade qa-browser-validation para utilizar o Browser Subagent. Navegue pela aplicação simulando um usuário para validar os Critérios de Aceite da US. Apresente os resultados ou screenshots ao usuário.
5. **Entrega e Commit:**
   - Atue como @sec para verificar se todos os testes passaram e se nada sensível vazará no commit.
   - Se aprovado, atue como @devops INVOCANDO OBRIGATORIAMENTE a habilidade pr-commit-standards. Prepare os comandos de commit utilizando a semântica correta (Conventional Commits) e a união via git merge --no-ff.
   - **Peça autorização final** antes de rodar os comandos do Git.
