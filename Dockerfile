### Dockerfile ###
FROM eclipse-temurin:17-jdk-alpine

# Set working directory
WORKDIR /app

# Add a volume (optional for devtools/hot reload)
VOLUME /tmp

# Copy the built jar file
COPY build/libs/*.jar app.jar

# Expose Spring Boot default port
EXPOSE 8080

# Run the jar
ENTRYPOINT ["java", "-jar", "app.jar"]