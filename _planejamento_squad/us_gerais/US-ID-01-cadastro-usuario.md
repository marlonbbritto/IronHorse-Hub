# US-ID-01: Cadastro de Usuário (Contexto: Identidade)

## 1. Descrição de Negócio
- **Como** um novo proprietário de moto,
- **Quero** criar uma conta utilizando meu e-mail e uma senha,
- **Para que** eu possa acessar a plataforma, cadastrar minha moto e gerenciar minhas informações.

## 2. Validação INVEST
- **Independente**: Sim.
- **Negociável**: Sim.
- **Valorável**: Sim.
- **Estimável**: Sim.
- **Pequena**: Sim.
- **Testável**: Sim.

## 3. Critérios de Teste e Aceite (QA)
- **CT-01 (Sucesso)**: Deve criar o usuário e retornar HTTP 201 Created quando recebido e-mail único e senha válida.
- **CT-02 (Falha - Duplicidade)**: Deve retornar HTTP 409 Conflict quando o e-mail enviado já constar no banco de dados.
- **CT-03 (Falha - Formato)**: O payload deve ser recusado (HTTP 400 Bad Request) se o e-mail for malformado ou se a senha estiver vazia.
- **Segurança**: A senha crua NUNCA transita de volta à interface gráfica e deve ser persistida via Bcrypt na tabela `users`.

## 4. Solução Técnica (Arquitetura Hexagonal - Java/Spring)
### Domain Layer
- Entidade de domínio `User` (id: UUID, email: string, passwordHash: string).
- Porta de saída `UserRepository` interface.

### Application Layer
- Porta de entrada `RegisterUserUseCase`.
- Serviço `RegisterUserService` (Bcrypt para hashear a senha, erro ao duplicar email).

### Infrastructure Layer (Adapters)
- **Entrada (Web)**: `UserController` (`POST /api/v1/users/register`), validações de DTO.
- **Saída (DB)**: Entidade `UserJpaEntity` (tabela `users`), `JpaUserRepository`, e adaptador `UserRepositoryAdapter`.
