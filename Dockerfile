# ====== Stage 1: Build (Maven) ======
FROM maven:3.9.8-eclipse-temurin-17 AS build
WORKDIR /build

# Cache dependencies first (faster rebuilds)
COPY pom.xml .
RUN mvn -q -DskipTests dependency:go-offline

# Now add sources and build
COPY src ./src
RUN mvn -q -DskipTests package

# ====== Stage 2: Run (JRE only) ======
FROM eclipse-temurin:17-jre
WORKDIR /app

# Copy the fat jar from the build stage
COPY --from=build /build/target/*.jar /app/app.jar

# Default profile for Render (can be overridden by env var)
ENV SPRING_PROFILES_ACTIVE=prod

# Optional for local clarity; Render ignores EXPOSE
EXPOSE 8080

# Use shell so $PORT and $SPRING_PROFILES_ACTIVE expand at runtime
ENTRYPOINT ["sh","-c","java -Dserver.port=$PORT -Dspring.profiles.active=${SPRING_PROFILES_ACTIVE:-prod} -jar /app/app.jar"]

