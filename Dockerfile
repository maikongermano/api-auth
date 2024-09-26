# Etapa de build
FROM ubuntu:latest AS build

RUN apt-get update
RUN apt-get install openjdk-17-jdk -y
COPY . .

RUN apt-get install maven -y
# Execute a construção do projeto
RUN mvn clean install 

# Etapa de execução
FROM openjdk:17-jdk-slim

EXPOSE 8080

# Copie o JAR construído da etapa de build
COPY --from=build /target/api-auth-0.0.1.jar app.jar

# Comando para executar a aplicação
ENTRYPOINT [ "java", "-jar", "app.jar" ]
