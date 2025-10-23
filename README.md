📚 Sistema de Biblioteca Digital (Quarkus + JPA + JSF)
Este projeto é uma aplicação completa para um Sistema de Biblioteca Digital construído com a plataforma Jakarta EE e o framework Quarkus. Ele serve como uma demonstração prática de uma arquitetura empresarial em camadas, focando na persistência de dados com JPA e na interface do usuário com Jakarta Faces.
🚀 Tecnologias Utilizadas
Framework Quarkus Otimizado para cloud-native e Java mais rápido.
Jakarta Faces (JSF) Camada de Apresentação (View) com Managed Beans CDI.
JPA / Hibernate ORMMapeamento Objeto-Relacional e gerenciamento de relacionamentos (One-to-Many, Many-to-One).
CDI (Contexts and Dependency Injection)Gerenciamento de componentes (Beans), Services e Repositórios.
PostgreSQLDriver JDBC configurado para o banco de dados.
Maven Gerenciamento de dependências e construção do projeto.
🏗️ Arquitetura e Estrutura do Projeto
O projeto adota uma arquitetura em camadas clara, separando as responsabilidades de forma coesa:
com.biblioteca.entity      Modelo/Entidade     Mapeamento JPA (@Entity) das tabelas (Autor, Livro, Emprestimo).
com.biblioteca.repository  Persistência        Contém a lógica de acesso a dados (DAOs) usando EntityManager e consultas JPQL.
com.biblioteca.service     Negócio             Contém a lógica de negócios (@Transactional) e orquestra as operações entre repositórios
.com.biblioteca.controller Apresentação        Managed Beans (@Named) do JSF, que interagem com a camada de Serviço e preparam dados para a View.
src/main/resources/META-INF/resources/View (JSF)Arquivos .xhtml da interface web (index.xhtml)
.src/main/resources/application.propertiesConfiguraçãoConfigurações centrais do Quarkus, Datasource e Hibernate.
src/main/resources/import.sqlCarga de DadosScript executado na inicialização para popular o banco de dados de desenvolvimento.
⚙️ Pré-requisitos
Para executar este projeto, você precisará:
JDK 17+
Apache Maven 3.8+
Um servidor PostgreSQL em execução (configurado na porta padrão 5432).

🚀 Como Executar a Aplicação
Siga os passos abaixo para configurar e iniciar o sistema de biblioteca localmente.
1. Configurar o Banco de DadosCrie o usuário e o banco de dados no seu servidor PostgreSQL. As credenciais a seguir são as definidas no seu application.properties:
'''SQL
-- Criar usuário e database no PostgreSQL
CREATE USER biblioteca WITH PASSWORD 'adm123';
CREATE DATABASE biblioteca_digital WITH OWNER biblioteca;

Nota: O application.properties no seu enunciado usa biblioteca para o nome do banco de dados, mas o script SQL do passo 1 usa biblioteca_digital.
Recomenda-se ajustar o application.properties para biblioteca_digital ou vice-versa, para garantir a consistência.

2. Verificar o application.properties
 Confirme se o arquivo src/main/resources/application.properties contém as seguintes configurações essenciais.
 Se suas credenciais do PostgreSQL forem diferentes, ajuste as propriedades de username e password.
  
'''Properties
# Configuração do banco de dados
quarkus.datasource.db-kind=postgresql
quarkus.datasource.username=biblioteca
quarkus.datasource.password=adm123
quarkus.datasource.jdbc.url=jdbc:postgresql://localhost:5432/biblioteca_digital

# Hibernate ORM (JPA)
# ATENÇÃO: 'drop-and-create' RECRIA o schema e o popula com import.sql a cada inicialização (Modo Dev)
quarkus.hibernate-orm.database.generation=drop-and-create
quarkus.hibernate-orm.log.sql=true
quarkus.hibernate-orm.sql-load-script=import.sql

# Configuração da View (Jakarta Faces)
quarkus.faces.welcome-files=index.xhtml

3. Executar a Aplicação (Modo de Desenvolvimento)Navegue até a raiz do projeto no seu terminal e execute o comando Maven para iniciar o Quarkus em modo de desenvolvimento (que inclui Live Reload):Bash./mvnw compile quarkus:dev
Aguarde o console exibir: Quarkus started in XXXs. Listening on: http://localhost:80804. Acessar a Interface WebApós a inicialização, acesse a aplicação no seu navegador. O arquivo index.xhtml será a página inicial:http://localhost:8080/
Você deve ver as tabelas de Autores, Livros e Empréstimos preenchidas com os dados do import.sql e as estatísticas calculadas pela Camada de Serviço.
