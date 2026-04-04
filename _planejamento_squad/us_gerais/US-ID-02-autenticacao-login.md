# US-ID-02: Autenticação e Sessão (Login)

## 1. Descrição de Negócio
- **Como** proprietário logado,
- **Quero** realizar o login na aplicação fornecendo meu e-mail e senha,
- **Para que** eu consiga obter uma sessão segura que me permita acessar os dados privados da minha garagem e perfil.

## 2. Validação INVEST
- **Independente**: Sim (pode evoluir para OAuth/Social).
- **Negociável**: Sim.
- **Valorável**: Sim (Segurança Core).
- **Estimável**: Sim.
- **Pequena**: Sim.
- **Testável**: Sim.

## 3. Critérios de Teste e Aceite (QA)
- **CT-01 (Sucesso)**: Quando um usuário ativo informa credenciais válidas, deve receber status HTTP 200/OK e o Token de acesso.
- **CT-02 (Falha - Não Encontrado)**: Resposta de segurança genérica (HTTP 401 Unauthorized) para não dar dicas a atacantes se o e-mail não existir.
- **CT-03 (Falha - Senha Incorreta)**: A mesma resposta genérica (HTTP 401 Unauthorized) quando o Hash da senha não bater.
- **CT-04 (Restrição de Rotas)**: Acessar funcionalidades da API sem o Token fornecido deve obrigatoriamente retornar HTTP 403 Forbidden ou 401.

## 4. Solução Técnica (Arquitetura Hexagonal - Java/Spring)
### Domain Layer
- Criação de interface (Porta) para lidar com a geração do Token: `TokenProvider`.
- Entidade `AuthToken` (Value Object).

### Application Layer
- Porta de entrada `AuthenticateUserUseCase` (recebe credenciais, retorna JWT Token).
- Serviço `AuthenticateUserService` para validar credenciais. Requer o uso da `UserRepository` e do `PasswordEncoder`.

### Infrastructure Layer (Adapters)
- **Entrada (Web)**: `LoginController` expondo o endpoint `POST /api/v1/users/login`.
- **Identidade/Segurança**: Adaptador `JwtTokenAdapter` implementando a porta `TokenProvider` utilizando `jjwt` (Java JWT).
- **Configuração**: Evolução do `SecurityConfig` para proteger rotas privadas e validar requisições autenticadas.
