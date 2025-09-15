# ---------- BUILD ----------
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app

# Descarga dependencias primero (mejor cache)
COPY pom.xml ./
# Usa BuildKit para cachear el repo local de Maven
RUN --mount=type=cache,target=/root/.m2 mvn -q -e -DskipTests dependency:go-offline

# Copia el código y compila
COPY src ./src
RUN --mount=type=cache,target=/root/.m2 mvn -q -e -DskipTests package

# ---------- RUNTIME ----------
FROM eclipse-temurin:21-jre
WORKDIR /app

# Copia el jar construido
ARG JAR_FILE=target/*.jar
COPY --from=build /app/${JAR_FILE} /app/app.jar

EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]
