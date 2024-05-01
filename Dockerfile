# Use a base image with Java 21 installed
FROM openjdk:17

# Set the working directory in the container
WORKDIR /app

# Copy the JAR file into the container
COPY target/github-popularity-service*[A-Z|0-9]-jar-with-dependencies.jar /app/github-popularity-service.jar

# Expose port 8080 to the outside world
EXPOSE 8080

# Command to run the application when the container starts
CMD ["java", "-jar", "github-popularity-service.jar"]
