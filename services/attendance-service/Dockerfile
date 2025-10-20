
FROM openjdk:17-jdk-slim

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN apt-get update && apt-get install -y maven
RUN mvn clean package -DskipTests

EXPOSE 8081

HEALTHCHECK --interval=30s --timeout=3s --start-period=5s --retries=3 \
    CMD curl -f http://localhost:8081/actuator/health || exit 1

CMD ["java", "-jar", "target/attendance-service-0.0.1-SNAPSHOT.jar"]