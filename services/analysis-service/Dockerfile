FROM eclipse-temurin:21-jdk-jammy
WORKDIR /app
COPY target/analysis-service-1.0.0.jar /app/app.jar
EXPOSE 8081
ENTRYPOINT ["java","-jar","/app/app.jar"]
