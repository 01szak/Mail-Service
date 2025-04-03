# Etap budowania
FROM openjdk:17-jdk-slim AS build

# Ustawienie katalogu roboczego
WORKDIR /app

# Kopiowanie plików Gradle i źródeł aplikacji
COPY gradlew ./
COPY gradle ./gradle
COPY build.gradle ./
COPY settings.gradle ./
COPY src ./src

# Instalacja zależności i kompilacja aplikacji
RUN chmod +x gradlew && ./gradlew build --no-daemon

# Etap produkcyjny
FROM openjdk:17-jdk-slim

# Ustawienie katalogu roboczego
WORKDIR /app

# Kopiowanie zbudowanego pliku .jar z etapu build
COPY --from=build /app/build/libs/mailService-0.0.1-SNAPSHOT.jar app.jar

# Otwieranie portu aplikacji
EXPOSE 8080

# Ustawienie komendy startowej
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
