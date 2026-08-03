# Multi-stage Dockerfile para Spring Boot con Java 21

# Etapa 1: Compilación de la aplicación con Maven y JDK 21
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Etapa 2: Imagen final de ejecución liviana con JRE 21
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /app/target/ecommerce-system-0.0.1-SNAPSHOT.jar app.jar

# Exponer el puerto configurado en el backend
EXPOSE 8087

# Ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]
