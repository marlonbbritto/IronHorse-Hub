# Definição da Stack Tecnológica - IronHorse Hub

Este arquivo define a pilha tecnológica oficial para o projeto de gestão de eventos e manutenção Harley-Davidson.

## Backend
- **Linguagem:** Java 21 (LTS)
- **Framework:** Spring Boot 3.x
- **Banco de Dados:** PostgreSQL (Instância Gratuita via Neon.tech ou Supabase)
- **Ferramentas:** Maven, Spring Data JPA, Spring Security (OAuth2/JWT)

## Frontend
- **Linguagem:** TypeScript
- **Framework:** Angular LTS
- **Estilização:** Tailwind CSS ou Angular Material
- **Estado:** Signals ou RxJS

## Arquitetura e Padrões Globais
- **Padrão Arquitetural:** Arquitetura Hexagonal (Domain, Application, Infrastructure)
- **Comunicação:** RESTful APIs (JSON)
- **Mensageria (Opcional):** RabbitMQ (Local via Docker para testes de microserviços)
