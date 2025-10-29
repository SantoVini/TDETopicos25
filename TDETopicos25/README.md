Como Containerizar a aplicação Quarkus

Este tutorial mostra como empacotar e executar a aplicação Quarkus em um container Docker, juntamente com um banco de dados PostgreSQL, utilizando o Docker Compose.

Isso permite que qualquer pessoa rode o sistema em qualquer máquina com apenas um comando, sem precisar configurar manualmente Java, Maven ou PostgreSQL localmente. É uma melhoria importante porque:

-Garante ambiente padronizado entre desenvolvedores e produção.
-Facilita o deploy em servidores e nuvem.
-Simplifica o processo de inicialização: docker compose up e pronto!

⚙️ Pré-requisitos

Antes de começar, você precisa ter instalado em sua máquina:

1 - Criando o Dockerfile
Dentro do diretório raiz do projeto (onde está o pom.xml), crie um arquivo chamado Dockerfile:
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
