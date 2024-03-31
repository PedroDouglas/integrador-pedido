# Integrador Pedido

O Integrador Pedido é um sistema desenvolvido em Java que oferece funcionalidades para gerenciamento de produtos e registro de vendas.

Este é um teste técnico, por isso foi determinada algumas limitações como o uso do JPA.
Devido está limitação foi utilizado como comunição com o banco MySQL o JDBC.

## Funcionalidades

- Cadastro, listagem, edição e exclusão de produtos.
- Registro de vendas, com possibilidade de adicionar e remover itens da venda.

## Tecnologias Utilizadas

- Java
- Spring Boot
- JDBC
- Spring Web
- ModelMapper
- Banco de dados MySQL

## Configuração do Ambiente de Desenvolvimento

1. Certifique-se de ter o Java JDK instalado em sua máquina. Você pode baixá-lo em [java.com](https://www.java.com/pt-BR/download/).
2. Instale o Apache Maven. Você pode baixá-lo em [maven.apache.org](https://maven.apache.org/).
3. Configure o banco de dados MySQL. Você pode encontrar informações sobre como configurar o MySQL em [dev.mysql.com](https://dev.mysql.com/doc/).
4. Clone este repositório em sua máquina local usando o seguinte comando:
5. Importe o projeto em sua IDE preferida (Eclipse, IntelliJ, etc.).
6. Configure as credenciais do banco de dados no arquivo `application.properties` localizado em `src/main/resources`.
7. Execute a aplicação. Isso iniciará o servidor Spring Boot local.

## Endpoints da API

A API do Integrador Pedido oferece os seguintes endpoints:

- `POST /api/produtos`: Cadastra um novo produto.
- `GET /api/produtos/{id}`: Retorna os detalhes de um produto específico.
- `GET /api/produtos`: Retorna a lista de todos os produtos cadastrados.
- `DELETE /api/produtos/{id}`: Exclui um produto específico.
- `PUT /api/produtos/{id}/adicionar-quantidade`: Adiciona quantidade a um produto específico.
- `PUT /api/produtos/{id}/remover-quantidade`: Remove quantidade de um produto específico.
- `POST /api/vendas`: Registra uma nova venda.
- `POST /api/vendas/{idVenda}/adicionarItem`: Adiciona um item a uma venda existente.
- `DELETE /api/vendas/{vendaId}/removerItem/{produtoId}`: Remove um item de uma venda existente.

## Contribuição

Contribuições são bem-vindas! Para contribuir com o Integrador Pedido, siga estas etapas:

1. Faça um fork do projeto
2. Crie uma branch para sua feature (`git checkout -b feature/NovaFeature`)
3. Faça commit das suas alterações (`git commit -am 'Adiciona NovaFeature'`)
4. Faça push para a branch (`git push origin feature/NovaFeature`)
5. Abra um pull request
