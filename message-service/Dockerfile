FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY target/message-service-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080 5005
ENTRYPOINT ["java", "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005", "-jar", "app.jar"]

