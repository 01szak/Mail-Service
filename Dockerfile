# Etap budowania
FROM openjdk:17-jdk-slim AS build

WORKDIR /app

# Kopiowanie całego katalogu projektu do kontenera
COPY gradlew ./
COPY gradle ./gradle
COPY build.gradle ./build.gradle
COPY src ./src

# Nadanie uprawnień do uruchamiania skryptu gradlew
RUN chmod +x gradlew && ./gradlew build --no-daemon



# Etap produkcyjny
FROM openjdk:17-jdk-slim

WORKDIR /app

# Kopiowanie JAR-a z poprzedniego etapu
COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8080

CMD ["./gradlew", "bootRun"]