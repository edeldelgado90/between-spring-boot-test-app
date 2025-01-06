# Use an image for Java 21
FROM amazoncorretto:21.0.4-alpine3.18

# Worker directory in the container
WORKDIR /app

# Copy the generated jar to the container
COPY target/*.jar app.jar

# Expose the ports for HTTP and gRPC
EXPOSE 8080 9090

# Command to run the application
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
