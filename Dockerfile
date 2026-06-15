FROM maven:3.9.11-eclipse-temurin-25 as build
WORKDIR /app
COPY pom.xml .
COPY src/ src/
RUN mvn -f pom.xml -Pprod clean package

FROM eclipse-temurin:25-jre as run
WORKDIR /app
RUN useradd tennis
USER tennis

COPY --from=build /app/target/api-tennis.jar app.jar
ENTRYPOINT ["java", "-jar", "/app/app.jar"]