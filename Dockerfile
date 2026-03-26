# Estágio 1: Build
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Estágio 2: Runtime
FROM eclipse-temurin:21-jre
WORKDIR /app

# O SEGREDO PARA O COMPOSE:
# Copia qualquer .jar da target e salva como app.jar na pasta atual
COPY --from=build /app/target/*.jar ./app.jar

EXPOSE 8080

# O comando de execução agora é fixo e seguro
ENTRYPOINT ["java", "-jar", "app.jar"]