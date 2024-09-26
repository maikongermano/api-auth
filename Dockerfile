# Etapa de build
FROM openjdk:17-jdk-slim AS build
COPY pom.xml mvnw ./
COPY .mvn .mvn

RUN chmod +x ./mvnw

RUN ./mvnw dependency:resolve

COPY src src

# Execute a construção do projeto
RUN ./mvnw package -DskipTests
FROM openjdk:17-jdk-slim

WORKDIR app

COPY --from=build target/*.jar app.jar
# Etapa de execução
ENTRYPOINT ["java", "-jar", "app.jar"]