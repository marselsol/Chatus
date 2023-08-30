FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY target/api-gateway-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8888
CMD ["java", "-jar", "app.jar"]