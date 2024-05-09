FROM openjdk:17.0.1-jdk-slim

WORKDIR /usr/app

COPY target/quarkus-app/ ./

EXPOSE 8080

CMD ["java", "-jar", "quarkus-run.jar"]
