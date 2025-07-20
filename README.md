# Fórum Hub Challenge Back End

Este projeto implementa uma API REST para um fórum de discussões, conforme o desafio proposto pela Alura. A API permite gerenciar tópicos, incluindo operações de criação, listagem, detalhamento, atualização e exclusão (CRUD), além de um sistema básico de autenticação e autorização com JWT.

## Tecnologias Utilizadas

*   **Java 17**
*   **Spring Boot 3**
*   **Spring Data JPA**
*   **MySQL**
*   **Spring Security**
*   **JWT (JSON Web Tokens)**
*   **Lombok**
*   **Maven**

## Diagrama do Banco de Dados

O modelo de dados segue o diagrama fornecido:

```mermaid
ERD
    Topico {
        long id PK
        string titulo
        string mensagem
        datetime dataCriacao
        boolean status
        long autor_id FK
        long curso_id FK
    }

    Usuario {
        long id PK
        string nome
        string email
        string senha
    }

    Curso {
        long id PK
        string nome
        string categoria
    }

    Resposta {
        long id PK
        string mensagem
        datetime dataCriacao
        boolean solucao
        long topico_id FK
        long autor_id FK
    }

    Perfil {
        long id PK
        string nome
    }

    Usuario ||--o{ Perfil : perfis
    Topico ||--o{ Usuario : autor
    Topico ||--o{ Curso : curso
    Topico ||--o{ Resposta : respostas
    Resposta ||--o{ Usuario : autor
```

## Pré-requisitos

Antes de executar o projeto, certifique-se de ter os seguintes softwares instalados:

*   **Java Development Kit (JDK) 17**
*   **Apache Maven**
*   **MySQL Server**

## Configuração do Banco de Dados

1.  **Crie um banco de dados** chamado `forumhub` no seu servidor MySQL.

    ```sql
    CREATE DATABASE IF NOT EXISTS forumhub;
    ```

2.  **Crie um usuário** `forumuser` com a senha `password` e conceda todas as permissões ao banco de dados `forumhub`.

    ```sql
    CREATE USER IF NOT EXISTS 'forumuser'@'localhost' IDENTIFIED BY 'password';
    GRANT ALL PRIVILEGES ON forumhub.* TO 'forumuser'@'localhost';
    FLUSH PRIVILEGES;
    ```

    *Nota: As credenciais do banco de dados estão configuradas no arquivo `src/main/resources/application.properties`.*

## Como Rodar o Projeto

1.  **Clone o repositório** (ou descompacte o arquivo do projeto) para o seu ambiente local.

2.  **Navegue até o diretório raiz do projeto** (`forumhub`).

3.  **Compile o projeto** usando Maven:

    ```bash
    mvn clean install
    ```

4.  **Execute a aplicação** Spring Boot:

    ```bash
    java -jar target/forumhub-0.0.1-SNAPSHOT.jar
    ```

    A aplicação será iniciada na porta `8080` por padrão.

## Endpoints da API

A API possui os seguintes endpoints:

### Autenticação

*   **`POST /login`**
    *   **Descrição:** Autentica um usuário e retorna um token JWT.
    *   **Corpo da Requisição (JSON):**
        ```json
        {
            "email": "seu_email@example.com",
            "senha": "sua_senha"
        }
        ```
    *   **Resposta (JSON):**
        ```json
        {
            "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
        }
        ```

### Usuários

*   **`POST /usuarios`**
    *   **Descrição:** Cadastra um novo usuário.
    *   **Corpo da Requisição (JSON):**
        ```json
        {
            "nome": "Nome do Usuário",
            "email": "email@example.com",
            "senha": "senha123"
        }
        ```
    *   **Resposta (JSON):** Retorna os detalhes do usuário criado (status `201 Created`).

*   **`GET /usuarios`**
    *   **Descrição:** Lista todos os usuários.
    *   **Resposta (JSON):** Lista de usuários.

*   **`GET /usuarios/{id}`**
    *   **Descrição:** Detalha um usuário específico pelo ID.
    *   **Resposta (JSON):** Detalhes do usuário.

*   **`DELETE /usuarios/{id}`**
    *   **Descrição:** Exclui um usuário pelo ID.
    *   **Resposta:** Status `204 No Content`.

### Cursos

*   **`POST /cursos`**
    *   **Descrição:** Cadastra um novo curso.
    *   **Corpo da Requisição (JSON):**
        ```json
        {
            "nome": "Nome do Curso",
            "categoria": "Categoria do Curso"
        }
        ```
    *   **Resposta (JSON):** Retorna os detalhes do curso criado (status `201 Created`).

*   **`GET /cursos`**
    *   **Descrição:** Lista todos os cursos.
    *   **Resposta (JSON):** Lista de cursos.

*   **`GET /cursos/{id}`**
    *   **Descrição:** Detalha um curso específico pelo ID.
    *   **Resposta (JSON):** Detalhes do curso.

*   **`PUT /cursos`**
    *   **Descrição:** Atualiza um curso existente.
    *   **Corpo da Requisição (JSON):**
        ```json
        {
            "id": 1,
            "nome": "Novo Nome do Curso",
            "categoria": "Nova Categoria"
        }
        ```
    *   **Resposta (JSON):** Retorna os detalhes do curso atualizado.

*   **`DELETE /cursos/{id}`**
    *   **Descrição:** Exclui um curso pelo ID.
    *   **Resposta:** Status `204 No Content`.

### Tópicos (Requer Autenticação - Token JWT no cabeçalho `Authorization: Bearer <token_jwt>`)

*   **`POST /topicos`**
    *   **Descrição:** Cria um novo tópico.
    *   **Corpo da Requisição (JSON):**
        ```json
        {
            "titulo": "Dúvida sobre Spring Security",
            "mensagem": "Como configurar o Spring Security para JWT?",
            "autorId": 1, 
            "cursoId": 1
        }
        ```
    *   **Resposta (JSON):** Retorna os detalhes do tópico criado (status `201 Created`).

*   **`GET /topicos`**
    *   **Descrição:** Lista todos os tópicos ativos (status `true`). Suporta paginação.
    *   **Parâmetros de Query (Opcional):**
        *   `page`: Número da página (padrão: 0)
        *   `size`: Quantidade de itens por página (padrão: 10)
        *   `sort`: Campo para ordenação (ex: `dataCriacao,desc`)
    *   **Resposta (JSON):** Lista paginada de tópicos.

*   **`GET /topicos/{id}`**
    *   **Descrição:** Detalha um tópico específico pelo ID.
    *   **Resposta (JSON):** Detalhes do tópico.

*   **`PUT /topicos`**
    *   **Descrição:** Atualiza um tópico existente.
    *   **Corpo da Requisição (JSON):**
        ```json
        {
            "id": 1,
            "titulo": "Nova Dúvida sobre Spring Security",
            "mensagem": "Preciso de ajuda com a configuração de filtros.",
            "status": true
        }
        ```
    *   **Resposta (JSON):** Retorna os detalhes do tópico atualizado.

*   **`DELETE /topicos/{id}`**
    *   **Descrição:** Exclui (inativa) um tópico pelo ID. O tópico não é removido fisicamente, apenas seu status é alterado para `false`.
    *   **Resposta:** Status `204 No Content`.

### Respostas (Requer Autenticação - Token JWT no cabeçalho `Authorization: Bearer <token_jwt>`)

*   **`POST /respostas`**
    *   **Descrição:** Adiciona uma nova resposta a um tópico.
    *   **Corpo da Requisição (JSON):**
        ```json
        {
            "mensagem": "Sua resposta aqui.",
            "topico": {"id": 1}, 
            "autor": {"id": 1}
        }
        ```
    *   **Resposta (JSON):** Retorna os detalhes da resposta criada (status `201 Created`).

*   **`GET /respostas`**
    *   **Descrição:** Lista todas as respostas.
    *   **Resposta (JSON):** Lista de respostas.

*   **`GET /respostas/{id}`**
    *   **Descrição:** Detalha uma resposta específica pelo ID.
    *   **Resposta (JSON):** Detalhes da resposta.

*   **`PUT /respostas`**
    *   **Descrição:** Atualiza uma resposta existente.
    *   **Corpo da Requisição (JSON):**
        ```json
        {
            "id": 1,
            "mensagem": "Nova mensagem da resposta.",
            "solucao": true
        }
        ```
    *   **Resposta (JSON):** Retorna os detalhes da resposta atualizada.

*   **`DELETE /respostas/{id}`**
    *   **Descrição:** Exclui uma resposta pelo ID.
    *   **Resposta:** Status `204 No Content`.

## Estrutura do Projeto

```
forumhub
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com
│   │   │       └── forumhub
│   │   │           ├── controller
│   │   │           │   ├── AutenticacaoController.java
│   │   │           │   ├── CursoController.java
│   │   │           │   ├── RespostaController.java
│   │   │           │   ├── TopicosController.java
│   │   │           │   └── UsuarioController.java
│   │   │           ├── dto
│   │   │           │   ├── DadosAtualizacaoTopico.java
│   │   │           │   ├── DadosAutenticacao.java
│   │   │           │   ├── DadosCadastroTopico.java
│   │   │           │   ├── DadosCadastroUsuario.java
│   │   │           │   ├── DadosDetalhamentoTopico.java
│   │   │           │   ├── DadosListagemTopico.java
│   │   │           │   └── DadosTokenJWT.java
│   │   │           ├── infra
│   │   │           │   ├── SecurityConfigurations.java
│   │   │           │   ├── SecurityFilter.java
│   │   │           │   └── TratadorDeErros.java
│   │   │           ├── model
│   │   │           │   ├── Curso.java
│   │   │           │   ├── Perfil.java
│   │   │           │   ├── Resposta.java
│   │   │           │   ├── Topico.java
│   │   │           │   └── Usuario.java
│   │   │           ├── repository
│   │   │           │   ├── CursoRepository.java
│   │   │           │   ├── PerfilRepository.java
│   │   │           │   ├── RespostaRepository.java
│   │   │           │   ├── TopicoRepository.java
│   │   │           │   └── UsuarioRepository.java
│   │   │           ├── service
│   │   │           │   ├── AutenticacaoService.java
│   │   │           │   └── TokenService.java
│   │   │           └── ForumhubApplication.java
│   │   └── resources
│   │       └── application.properties
│   └── test
│       └── java
│           └── com
│               └── forumhub
│                   └── ForumhubApplicationTests.java
├── pom.xml
└── README.md
```

---

**Autor:** Manus AI


