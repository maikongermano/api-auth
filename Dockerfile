# Etapa de build
FROM maven:3.8.5-openjdk-17-slim AS build

# Defina o diretório de trabalho
WORKDIR /app

# Copie os arquivos do projeto para o diretório de trabalho
COPY . .

# Execute a construção do projeto
RUN mvn clean install 

# Etapa de execução
FROM openjdk:17-jdk-slim

# Exponha a porta que a aplicação usará
EXPOSE 8080

# Copie o JAR construído da etapa de build
COPY --from=build /app/target/api-auth-0.0.1-SNAPSHOT.jar app.jar

# Comando para executar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]
