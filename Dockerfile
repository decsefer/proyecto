# Etapa 1: build con Maven Wrapper
FROM eclipse-temurin:17-jdk-alpine AS build

WORKDIR /app

# Copiamos el wrapper de Maven y el pom.xml
COPY .mvn/ .mvn
COPY mvnw pom.xml ./

# Descargamos dependencias (aprovecha cache)
RUN chmod +x mvnw && ./mvnw -q -B dependency:go-offline

# Copiamos el código fuente
COPY src ./src

# Construimos el JAR (sin tests)
RUN ./mvnw -q -B clean package -DskipTests

# Etapa 2: runtime, solo el JAR resultante
FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

LABEL authors="sergio rodriguez"
VOLUME /temp

# Copiamos el JAR ya compilado desde la etapa anterior
COPY --from=build /app/target/*.jar app.jar

# Render inyecta PORT, pero dejamos 8080 por defecto
ENV PORT=8080
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
