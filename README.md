# Como Containerizar a Aplicação Quarkus com PostgreSQL

Este tutorial mostra como empacotar e executar a aplicação Quarkus em um container Docker, juntamente com um banco de dados PostgreSQL, utilizando o Docker Compose.

Isso permite que qualquer pessoa rode o sistema em qualquer máquina com apenas um comando, sem precisar configurar manualmente Java, Maven ou PostgreSQL localmente. É uma melhoria importante porque:

Garante ambiente padronizado entre desenvolvedores e produção.
Facilita o deploy em servidores e nuvem.
Simplifica o processo de inicialização: docker compose up e pronto!

# Pré-requisitos
Antes de começar, você precisa ter instalado em sua máquina:

Docker;
Docker Compose

# 1- Criando o Dockfile
Dentro do diretório raiz do projeto (onde está o pom.xml), crie um arquivo chamado
```
# Etapa 1: Build da aplicação
FROM maven:3.9.9-eclipse-temurin-17 AS build
WORKDIR /app

# Copia os arquivos do projeto
COPY pom.xml .
COPY src ./src

# Gera o pacote (JAR)
RUN mvn clean package -DskipTests

# Etapa 2: Executando o aplicativo
FROM eclipse-temurin:17-jdk
WORKDIR /app

# Copia o JAR gerado do build
COPY --from=build /app/target/*-runner.jar app.jar

# Expõe a porta do Quarkus
EXPOSE 8080

# Comando para iniciar o aplicativo
ENTRYPOINT ["java", "-jar", "app.jar"]
```

# 2-Criando o arquivo docker-compose.yml
Na raiz do projeto, crie o arquivo docker-compose.yml:
```
yaml
version: "3.8"

services:
  postgres:
    image: postgres:16
    container_name: biblioteca-db
    environment:
      POSTGRES_USER: biblioteca
      POSTGRES_PASSWORD: biblioteca123
      POSTGRES_DB: biblioteca_db
    ports:
      - "5432:5432"
    volumes:
      - postgres_data:/var/lib/postgresql/data
    networks:
      - biblioteca-net

  quarkus-app:
    build: .
    container_name: biblioteca-app
    environment:
      QUARKUS_DATASOURCE_JDBC_URL: jdbc:postgresql://postgres:5432/biblioteca_db
      QUARKUS_DATASOURCE_USERNAME: biblioteca
      QUARKUS_DATASOURCE_PASSWORD: biblioteca123
      QUARKUS_HIBERNATE_ORM_DATABASE_GENERATION: update
    ports:
      - "8080:8080"
    depends_on:
      - postgres
    networks:
      - biblioteca-net

networks:
  biblioteca-net:
    driver: bridge

volumes:
  postgres_data:
```
# 3-Ajustando o aplication.properties
No arquivo src/main/resources/application.properties, substitua (ou adicione) as variáveis de conexão para que funcionem com o container:
```
# Configurações do Banco de Dados
quarkus.datasource.db-kind=postgresql
quarkus.datasource.username=${QUARKUS_DATASOURCE_USERNAME}
quarkus.datasource.password=${QUARKUS_DATASOURCE_PASSWORD}
quarkus.datasource.jdbc.url=${QUARKUS_DATASOURCE_JDBC_URL}

# Porta da aplicação
quarkus.http.port=8080
```
Essas variáveis são injetadas pelo docker-compose.yml automaticamente.

# 4- Subindo os containers
Agora basta rodar o comando:
```
docker compose up --build
```
