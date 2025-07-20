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

     de usuários.
