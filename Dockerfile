FROM eclipse-temurin:21-jdk-jammy

WORKDIR /app
COPY pom.xml ./
COPY src ./src

RUN ./mvnw -q -B -Dmaven.test.skip=true package

EXPOSE 8080
CMD ["java", "-jar", "target/AIComplaintIntelligencePlatform-1.0.0.jar"]
