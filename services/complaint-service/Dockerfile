FROM eclipse-temurin:21-jdk-jammy
WORKDIR /app
COPY target/complaint-service-1.0.0.jar /app/app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]
