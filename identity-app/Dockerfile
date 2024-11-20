FROM openjdk:17-jdk-slim
ARG JAR_FILE=target/ms-identity-0.0.1.jar
COPY ${JAR_FILE} ms-identity.jar
EXPOSE 8083
ENTRYPOINT ["java", "-jar", "ms-identity.jar"]