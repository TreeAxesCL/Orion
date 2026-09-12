FROM maven:3.9.9-eclipse-temurin-17 AS builder
WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline

COPY src ./src
RUN mvn clean package -DskipTests

FROM  eclipse-temurin:17-jdk-jammy
WORKDIR /app

ARG PORT_ORION_BACKEND=8000

COPY --from=builder /app/target/*.jar app.jar

EXPOSE ${PORT_ORION_BACKEND}

ENTRYPOINT ["java", "-jar", "app.jar"]