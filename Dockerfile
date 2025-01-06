# Use the Alpine Java 21 base image
FROM registry.tools.3stripes.net/base-images/alpine_java-21

# Set the working directory
WORKDIR /app

# Copy the JAR file into the image
COPY target/*.jar app.jar

# Expose the application port
EXPOSE 8080

# Command to run the application
CMD java -jar /app/app.jar
