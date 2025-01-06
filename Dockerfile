# Use the official OpenJDK Alpine image
FROM eclipse-temurin:21-alpine

# Set the working directory
WORKDIR /app

# Copy the JAR file into the image
COPY target/testfont-0.0.1-SNAPSHOT.jar app.jar

# Expose the application port
EXPOSE 8080

# Command to run the application
CMD java -jar /app/app.jar
