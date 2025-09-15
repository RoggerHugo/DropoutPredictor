# ====== STAGE 1: build ======
FROM eclipse-temurin:21-jdk AS build
WORKDIR /app

# Copiamos primero los POM para cachear dependencias
COPY pom.xml ./
# En caso de tener módulos, copia también sus POMs
# COPY module-a/pom.xml module-a/pom.xml
# COPY module-b/pom.xml module-b/pom.xml

# Descarga dependencias sin compilar tests
RUN --mount=type=cache,target=/root/.m2 mvn -q -e -DskipTests dependency:go-offline

# Copiamos el código
COPY src ./src

# Compilamos y generamos el JAR (cambia el goal si usas otro perfil)
RUN --mount=type=cache,target=/root/.m2 mvn -q -DskipTests clean package

# Extraemos el jar para capas (Spring Boot 3.x)
RUN java -Djarmode=layertools -jar target/*.jar extract

# ====== STAGE 2: runtime ======
FROM eclipse-temurin:21-jre
WORKDIR /app

# Variables (Render define PORT; aquí la honramos)
ENV PORT=8080 \
    TZ=Etc/UTC \
    JAVA_OPTS="-XX:MaxRAMPercentage=75.0"

# Copiamos capas del jar
COPY --from=build /app/dependencies/ ./
COPY --from=build /app/spring-boot-loader/ ./
COPY --from=build /app/snapshot-dependencies/ ./
COPY --from=build /app/application/ ./

# Healthcheck simple contra /actuator/health (opcional)
HEALTHCHECK --interval=30s --timeout=5s --start-period=20s --retries=3 \
  CMD wget -qO- "http://127.0.0.1:${PORT}/actuator/health" || exit 1

# Render enruta tráfico al puerto de la variable PORT
EXPOSE 8080

# Ejecuta el launcher de Spring Boot y respeta $PORT
ENTRYPOINT ["bash","-lc","java $JAVA_OPTS org.springframework.boot.loader.launch.JarLauncher --server.port=${PORT}"]
