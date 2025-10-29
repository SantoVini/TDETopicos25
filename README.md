Fantástico! A containerização é um passo essencial no DevOps para garantir portabilidade e isolamento da sua aplicação Quarkus e do PostgreSQL.

Aqui está o tutorial passo a passo, incluindo o Dockerfile para o Quarkus e o docker-compose.yml para orquestrar os dois serviços.

🚀 Tutorial: Containerizando Quarkus e PostgreSQL
O Quarkus geralmente gera um Dockerfile.jvm e um Dockerfile.native na pasta src/main/docker. Vamos usar o modo JVM para simplicidade e velocidade na demonstração.

1. 📦 Empacotar o Projeto Quarkus
Antes de construir a imagem Docker, você precisa compilar seu projeto Quarkus para gerar o JAR executável.

Abra o terminal na raiz do seu projeto Quarkus e execute o build:

Bash

./mvnw clean package
# OU
./gradlew clean build
O Quarkus gera o pacote runner no modo "fast-jar" dentro do diretório target/quarkus-app/ (se estiver usando Maven) ou similar.

2. 📝 Dockerfile para a Aplicação Quarkus (Modo JVM)
Crie um arquivo chamado Dockerfile na raiz do seu projeto (ou use o Dockerfile.jvm gerado em src/main/docker/, adaptando os caminhos se necessário).

Este Dockerfile fará um multi-stage build: ele usa uma imagem maior para o build e outra menor para a execução, resultando em uma imagem final leve.

Dockerfile

# Estágio de Build (Opcional, se o build não foi feito antes)
# FROM maven:3.9.5-eclipse-temurin-17-alpine AS build
# COPY . /build
# WORKDIR /build
# RUN mvn clean package -DskipTests

# Estágio de Execução
# Usando a imagem de execução base do Eclipse Temurin (JDK)
FROM registry.access.redhat.com/ubi8/openjdk-17:1.15 AS runtime
# Ou uma imagem OpenJDK oficial (menos otimizada que UBI, mas funcional)
# FROM eclipse-temurin:17-jdk-jammy

# O arquivo JAR executável do Quarkus está em 'target/quarkus-app'
# Se o seu build estiver em 'target/quarkus-app/', use este caminho:
COPY --chown=1001:0 target/quarkus-app /work/application

WORKDIR /work/
# O usuário 'jboss' (UID 1001) é comumente usado em imagens Red Hat para segurança
# Garante que o usuário tem permissão para executar
RUN chmod -R 775 application
USER 1001

# Expõe a porta padrão do Quarkus (8080)
EXPOSE 8080

# Comando para iniciar a aplicação Quarkus
CMD ["java", "-jar", "application/quarkus-run.jar"]
Nota: Se você estiver usando um projeto gerado pelo Quarkus, o Dockerfile.jvm já deve estar em src/main/docker/ e pode ser ligeiramente diferente, mas o conceito é o mesmo.

3. 📝 docker-compose.yml
Crie um arquivo chamado docker-compose.yml na raiz do seu projeto. Este arquivo define dois serviços: sua aplicação Quarkus (app) e o banco de dados PostgreSQL (db).

YAML

version: '3.8'

services:
  # Serviço da Aplicação Quarkus
  app:
    # Nome do container
    container_name: quarkus-app
    # Constrói a imagem Docker a partir do Dockerfile na pasta atual (o ponto '.')
    build: .
    # Mapeia a porta do container (8080) para a porta da sua máquina (8080)
    ports:
      - "8080:8080"
    # Garante que o BD esteja pronto antes de iniciar a aplicação
    depends_on:
      - db
    # Variáveis de ambiente que a aplicação Quarkus usará para conectar ao BD
    environment:
      # As propriedades de configuração do Quarkus devem ser definidas aqui
      # Verifique seu arquivo application.properties para os nomes corretos
      # Exemplo para a extensão Panache com JDBC:
      #quarkus.datasource.db-kind: postgresql
      #quarkus.datasource.username: ${POSTGRES_USER}
      #quarkus.datasource.password: ${POSTGRES_PASSWORD}
      #quarkus.datasource.jdbc.url: jdbc:postgresql://db:5432/${POSTGRES_DB}
      
      QUARKUS_DATASOURCE_USERNAME: ${POSTGRES_USER}
      QUARKUS_DATASOURCE_PASSWORD: ${POSTGRES_PASSWORD}
      # Usa o nome do serviço 'db' definido abaixo como hostname
      QUARKUS_DATASOURCE_JDBC_URL: jdbc:postgresql://db:5432/${POSTGRES_DB}
    
    # Reinicia o container se ele parar (exceto se for manualmente interrompido)
    restart: always

  # Serviço do Banco de Dados PostgreSQL
  db:
    container_name: postgres-db
    image: postgres:15-alpine # Imagem oficial do PostgreSQL
    # Mapeia a porta do container (5432) para a porta da sua máquina (5432)
    # Útil para acessar o BD com ferramentas externas como o DBeaver ou psql
    ports:
      - "5432:5432"
    # Variáveis de ambiente para configurar o PostgreSQL
    environment:
      # Use variáveis de ambiente para valores sensíveis
      POSTGRES_DB: ${POSTGRES_DB}
      POSTGRES_USER: ${POSTGRES_USER}
      POSTGRES_PASSWORD: ${POSTGRES_PASSWORD}
    # Persistência de dados: mapeia um volume da sua máquina para o diretório de dados do BD
    volumes:
      - postgres_data:/var/lib/postgresql/data
    restart: always

# Define o volume para persistir os dados do PostgreSQL
volumes:
  postgres_data:
4. 🔑 Arquivo de Variáveis de Ambiente (.env)
Crie um arquivo .env na raiz do seu projeto para armazenar as variáveis usadas no docker-compose.yml.

# Variáveis de Configuração do PostgreSQL
POSTGRES_DB=minha_aplicacao_db
POSTGRES_USER=quarkus_user
POSTGRES_PASSWORD=uma_senha_forte
Dica de Segurança: Adicione o arquivo .env ao seu .gitignore para não enviar senhas para repositórios públicos!

5. 🚀 Subir a Aplicação e o Banco de Dados
Com todos os arquivos prontos, você pode subir toda a sua infraestrutura isolada com um único comando:

Bash

docker compose up --build -d
docker compose up: Inicia os serviços definidos no docker-compose.yml.

--build: Garante que a imagem Docker da sua aplicação Quarkus seja construída antes de iniciar o container.

-d: Executa em detached mode (em segundo plano).

Sua aplicação Quarkus e seu banco de dados PostgreSQL estarão isolados e rodando!

Para verificar se os containers estão rodando:

Bash

docker ps
Para ver os logs da sua aplicação:

Bash

docker compose logs -f app
Para derrubar os containers e remover a rede (mantendo os dados, por causa do volume):

Bash

docker compose down
Para derrubar tudo, incluindo os dados persistidos:

Bash

docker compose down -v
Qual seria o seu próximo passo? Gostaria de ajuda para:

Ajustar o docker-compose.yml para um ambiente de produção?

Configurar o Dockerfile para gerar uma imagem nativa do Quarkus (muito menor e mais rápida)?

Integrar o docker-compose.yml com um arquivo de provisionamento inicial do PostgreSQL?
