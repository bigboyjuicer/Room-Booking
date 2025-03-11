FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app
COPY target/*.jar booktalk.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","booktalk.jar"]