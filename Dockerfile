# ===== Stage 1: Build =====
FROM maven:3.9.5-eclipse-temurin-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# ===== Stage 2: Run =====
FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# Tell Docker how to run your app
CMD ["java", "-jar", "app.jar"]

#it builds the app and then runs it in a clean image
