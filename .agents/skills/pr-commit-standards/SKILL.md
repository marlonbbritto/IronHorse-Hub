---
name: pr-commit-standards
description: "Padrões estritos e diretrizes obrigatórias para Commits atômicos no contexto de Pull Requests"
risk: safe
source: self
date_added: "2026-04-03"
---

# Padrões para Commits e Pull Requests

Garante um fluxo de trabalho seguro, com um histórico perfeitamente rastreável e facilitado para code reviews por meio de diretrizes estritas de controle de versão, aplicando-se também o que vimos na prática com mesclagens (merges e 
o-ff).

## Quando utilizar esta skill
- Durante o desenvolvimento de qualquer funcionalidade ou correção visando abertura de um Pull Request (PR).
- Ao particionar mudanças em commits locais antes de enviá-los ao repositório origin.
- Na aprovação e integração de código (merge de PRs).

---

## 1. Regra de Ouro do Tamanho do Commit

**Todo commit isolado DEVE ter, sempre que possível, menos de 300 linhas alteradas.**

1. **Atômico por Natureza:** O limite força os desenvolvedores a dividir suas resoluções em "pedaços lógicos" que façam sentido individualmente (ex: criar o DTO, dpois criar O controller, depois ajustar os testes).
2. **Review Eficaz:** PRs com grandes lotes de alterações escondem bugs em meio a centenas de linhas.
3. **Exceção à Regra:** Arquivos gerados automaticamente (como arquivos _lock_, migrações estruturais ou dumps inseparáveis) não entram na contabilidade estrita se sua divisão for tecnicamente impossível.

## 2. Conventional Commits (Obrigatório em Pull Requests)

Mantenha o padrão padronizado (escrito em Inglês Americano) para cada commit do Pull Request:

- **feat:** Nova funcionalidade (ex: eat(api): add image cropping feature).
- **fix:** Solução de um bug (ex: ix(auth): adjust JWT configuration to read from HttpOnly cookies).
- **test:** Adição ou refatoração de testes existentes.
- **refactor:** Refatoração estrutural (não muda comportamento ou funcionalidades aparentes).
- **chore:** Atualizações, dependências e builds.

A mensagem do Pull Request no GitHub deve refletir a entrega como um todo, usando o mesmo padrão semântico, muitas vezes comissionado pelo merge das partes lógicas do seu trabalho.

## 3. Preservação de Histórico de Merges

Quando você cria os maravilhosos pequenos commits lógicos menores que 300 linhas, **o seu esforço descritivo deve ser mantido**.
- Evite merge --squash ao mesclar um PR em branches principais, a não ser que os commits na branch da feature sejam exclusivamente de "wip" e não tenham sentido semântico.
- Dê preferência aos merges tradicionais (Pull Request normal gerando um merge commit, ex: merge --no-ff) para que as etapas arquiteturais detalhadas desenvolvidas (e documentadas pelos seus commits curtos) continuem acessíveis no histórico do Git da main/master para auditoria futura.

## 4. Checklist Pré-Push (Para Submissão a PRs)
- [ ] Meus commits são atômicos e cada um resolve ou implementa uma única etapa?
- [ ] Algum commit manual do meu código excede o limite estrito de **300 linhas** sem que fosse tecnicamente impossível particioná-lo?
- [ ] As mensagens baseiam-se em *Conventional Commits* e estão grafadas corretamente em inglês?
- [ ] Executei a suíte de testes (mvnw test ou similar) antes de originar o fluxo de push para garantir a integridade?
