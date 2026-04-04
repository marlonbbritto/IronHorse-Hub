# User Story: US01 - Cadastro de Moto

## Descrição de Negócio
**Como** proprietário de uma oficina ou entusiasta  
**Quero** cadastrar uma moto no IronHorse Hub  
**Para que** eu possa gerenciar a manutenção e o histórico do veículo de forma centralizada.

## Modelo INVEST
- **I**ndependent: Sim, o cadastro de motos é a funcionalidade base.
- **N**egotiable: Sim, os campos opcionais podem ser expandidos no futuro.
- **V**aluable: Sim, agrega valor ao permitir o rastreio de veículos.
- **E**stimable: Sim, esforço técnico bem definido.
- **S**mall: Sim, escopo atômico.
- **T**estable: Sim, critérios de aceite claros.

## Critérios de Teste e Aceite
| Campo | Tipo | Obrigatório |
|---|---|---|
| Modelo | Texto | Sim |
| Ano | Inteiro | Sim |
| KM | Decimal | Sim |
| Placa | Texto | Não |
| VIN | Texto | Não |

1. **Sucesso no Cadastro:** Ao enviar todos os dados obrigatórios, o sistema deve retornar status 201 e o objeto criado.
2. **Erro de Validação:** Se Modelo, Ano ou KM estiverem ausentes, o sistema deve retornar status 400.
3. **Persistência:** Os dados devem ser salvos corretamente na infraestrutura de banco de dados.

## Solução Técnica Definida (Arquitetura Hexagonal)
Conforme definido no `STACK.md`, a implementação seguirá a estrutura hexagonal:

- **Domain Layer:** 
  - Entidade de Domínio `Moto` com regras de validação.
  - Interface `MotoRepository` (Porta de Saída).
- **Application Layer:** 
  - User Case `CadastrarMotoService`.
  - DTOs de Entrada e Saída.
- **Infrastructure Layer:**
  - `MotoController` (Porta de Entrada/Adapter) via Spring Boot.
  - `JpaMotoRepository` implementing `MotoRepository` (Adapter de Saída).
  - Configurações do PostgreSQL.

---
*Gerado automaticamente pelo Squad de Desenvolvimento Autônomo.*
