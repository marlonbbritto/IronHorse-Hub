# 🚀 GoldFlow Antigravity: Squad de Desenvolvimento Autônomo

Este projeto define uma arquitetura de workspace local no **Google Antigravity** para orquestrar um squad de agentes de IA especializados que atuam no ciclo de desenvolvimento de software. O foco é garantir alta qualidade técnica, segurança e disciplina através de intervenções humanas apenas para aprovações estratégicas.

## 📦 Como Iniciar (Getting Started)

Para utilizar este framework, siga os passos abaixo após clonar o repositório:

### 1. Criar Estrutura de Planejamento Local
Por questões de segurança e governança, a pasta de planejamento técnico é ignorada pelo Git. Execute o comando abaixo no terminal para recriar a estrutura necessária para o funcionamento dos agentes:

    New-Item -ItemType Directory -Path "_planejamento_squad/blueprints", "_planejamento_squad/us_gerais", "_planejamento_squad/planos_implementacao" -Force

### 2. Definir a Stack Tecnológica
O framework é agnóstico. Abra o arquivo STACK.md localizado na raiz do projeto e defina as linguagens e frameworks que a sua equipe utilizará (ex: Node.js, Java, React, Angular). Os agentes lerão este arquivo para balizar o desenvolvimento.

### 3. Configurar o Antigravity (Blindagem Dupla)
Este projeto utiliza **Blindagem Dupla** para segurança: o Git é bloqueado pelo `.gitignore` (remoto), enquanto a I/O da IA é mediada pelo `.mcpignore`. 

Nas configurações (**Settings**) da IDE, aplique os seguintes padrões obrigatórios:
* **Agent Mode**: Planning Mode.
* **Artifact Review Policy**: Request Review.
* **Terminal Command Execution**: Request Review.
* **Agent Gitignore Access**: **On** (OBRIGATÓRIO para funcionamento do framework).

---

## 🧠 O Squad (Personas)
Cada agente possui um papel estrito definido no arquivo AGENTS.md:
* **@po (Product Owner)**: Requisitos de negócio e User Stories no modelo INVEST.
* **@architect**: Guardião da Arquitetura, SOLID e Clean Code.
* **@qa_eng**: Critérios de aceite e validação E2E via navegador.
* **@agile**: Decomposição de tarefas e disciplina da esteira ágil.
* **@backend_dev / @frontend_dev**: Execução técnica guiada pelo STACK.md.
* **@sec**: Auditor de segurança e guardião do .gitignore.
* **@devops**: Manipulação segura do terminal e fluxo de Git.

## 🛠️ Workflows Principais
Os fluxos são disparados via comandos de barra (/):
* **/criar-us**: Orquestra o planejamento técnico e visual antes da codificação.
* **/desenvolver-us**: Guia a implementação atômica com testes obrigatórios e commit seguro (via pr-commit-standards).

## 📂 Estrutura do Projeto
* _planejamento_squad/: (Ignorado pelo Git) Contém blueprints, US validadas e planos de implementação.
* .agents/: Contém a "inteligência" do squad (regras passivas, habilidades avançadas importadas e workflows).
* AGENTS.md: Definição central das personas e limites de cada agente.
* STACK.md: Template para definição das tecnologias do projeto.

## ⚖️ Padrões de Engenharia
* **Git**: Commits atômicos (máximo ~300 linhas), uso de Conventional Commits e flag --no-ff em todos os merges para preservação do histórico (Controlado pela skill pr-commit-standards).

---
*Este framework foi desenvolvido para garantir transparência e rastreabilidade total no desenvolvimento assistido por IA.*
