# DSCommerce

Projeto desenvolvido com Java e Spring Boot
Este projeto foi criado como parte do curso Java Spring Essential(DevSuperior), com o objetivo de construir uma aplicação completa de e-commerce backend. O DSCommerce é uma API RESTful estruturada para gerenciar produtos, categorias, usuários e pedidos, com autenticação e controle de acesso.

## Funcionalidades

### Endpoints públicos

##### GET /products: lista todos os produtos.
##### GET /products/{id}: retorna os detalhes de um produto específico.
##### GET /categories: lista todas as categorias.

### Autenticação
##### POST /oauth2/token: autentica o usuário e retorna o token de acesso.
##### Gerenciamento de produtos (acesso ADMIN):
##### POST /products: cria um novo produto.
##### PUT /products/{id}: atualiza os dados de um produto.
##### DELETE /products/{id}: exclui um produto.

### Pedidos
##### GET /orders/{id}: retorna detalhes de um pedido (apenas ADMIN ou o dono do pedido).
##### POST /orders: cria um novo pedido.

### Perfil do usuário:
##### GET /users/me: retorna os dados do usuário logado.

## Tecnologias e Ferramentas

##### Linguagem: Java
##### Framework: Spring Boot
##### Banco de Dados: H2
##### Segurança: Spring Security com autenticação OAuth2 e JWT
##### Gerenciamento de Dependências: Maven
##### Testes: Postman (para validar os endpoints)
