# 📄 BLUEPRINT_MACRO_IRONHORSE.md

## 1. Visão do Produto
O **IronHorse Hub** é uma plataforma centralizada para proprietários de motocicletas (foco inicial em Harley-Davidson) gerirem o ciclo de vida dos seus veículos, manutenção preventiva e integração com a comunidade através de eventos e rotas.

## 2. Mapeamento de Domínio - DDD
Identificamos quatro **Bounded Contexts** principais:
* **Contexto de Garagem (Core):** Gestão dos ativos (motos), especificações técnicas, quilometragem e posse.
* **Contexto de Manutenção (Core):** Regras de negócio para revisões, alertas baseados em KM/tempo e histórico de serviços.
* **Contexto de Eventos/Rotas (Supportive):** Planejamento de viagens, integração com mapas e check-ins em pontos de interesse.
* **Contexto de Identidade (Generic):** Gestão de perfis de usuários e autenticação (OAuth2/JWT).

## 3. Arquitetura de Sistema

### Diagrama de Contexto (C4 - Nível 1)
\\\mermaid
graph LR
    User((Proprietário de Moto)) --> IHH[IronHorse Hub]
    IHH --> Maps[Google Maps API]
    IHH --> Mail[Serviço de E-mail/Push]
    IHH --> Weather[API de Clima]
\\\

### Diagrama de Containers (C4 - Nível 2)
\\\mermaid
graph TD
    User((Proprietário)) --> SPA[Angular Web App]
    SPA --> API[Spring Boot API Gateway]
    API --> GarageService[Microserviço Garagem]
    API --> MaintService[Microserviço Manutenção]
    GarageService --> DB[(PostgreSQL)]
    MaintService --> DB[(PostgreSQL)]
\\\

## 4. ADR: Estratégia de Microserviços
* **Decisão:** Iniciaremos como um **Monólito Modular** (Modular Monolith).
* **Justificativa:** Para um MVP de teste, a sobrecarga de gerir múltiplos deploys e rede (latência) é desnecessária. A Arquitetura Hexagonal garantirá que os módulos sejam fracamente acoplados, permitindo que os serviços sejam extraídos no futuro sem refatoração de código de domínio.

## 5. Implementação da Arquitetura Hexagonal
Aplicaremos o padrão *Ports and Adapters*:
* **Domain (Core):** Contém as entidades (ex: Moto, Revisao) e regras de negócio puras.
* **Application (Ports):** Interfaces que definem como o mundo externo interage com o domínio.
* **Infrastructure (Adapters):** Implementações técnicas (ex: Repositories com JPA, Controllers REST com Spring Boot, Clientes HTTP).
