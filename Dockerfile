### Dockerfile ###
FROM eclipse-temurin:17-jdk-alpine

# Set working directory
WORKDIR /app

# Add a volume (optional for devtools/hot reload)
VOLUME /tmp

# Copy the built jar file which is created by running ./gradlew build in terminal
COPY build/libs/AdventisDentalMgmtApplication-1.0-SNAPSHOT.jar app.jar

# Expose Spring Boot default port
EXPOSE 8080

# Run the jar
ENTRYPOINT ["java", "-jar", "app.jar"]