FROM maven:3.9.4-eclipse-temurin-21 AS BUILDER

WORKDIR /app

COPY pom.xml mvnw ./
COPY .mvn/ .mvn
RUN chmod +x mvnw
RUN ./mvnw dependency:go-offline

COPY src ./src
RUN ./mvnw clean install -DskipTests

FROM eclipse-temurin:21.0.2_13-jre-jammy AS RUNNER

WORKDIR /app

EXPOSE 8080

COPY --from=BUILDER /app/target/*.jar /app/app.jar

ENTRYPOINT ["java", "-jar", "/app/app.jar"]