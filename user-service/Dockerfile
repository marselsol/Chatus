FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY target/user-service-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8084
CMD ["java", "-jar", "app.jar"]