# Use lightweight Java image
FROM eclipse-temurin:21-jdk

# Set working directory
WORKDIR /app

# Copy jar from target folder
COPY target/*.jar app.jar

# Expose port (Render will override)
EXPOSE 8080

# Run the app
ENTRYPOINT ["java", "-jar", "app.jar"]