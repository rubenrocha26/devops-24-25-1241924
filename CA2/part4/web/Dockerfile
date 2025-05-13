FROM eclipse-temurin:17-jdk AS builder

# Create a directory for the project
WORKDIR /web

# Copy the repository and navigate to the project directory
COPY CA1/part3/react-and-spring-data-rest-basic /web

# Navigate to the project directory
WORKDIR /web

# Change the permissions of the gradlew file to make it executable
RUN chmod +x gradlew

# Run the gradle build command
RUN ./gradlew build --no-daemon

FROM eclipse-temurin:17-jre

# Copy the generated JAR file to the Tomcat webapps directory
WORKDIR /web

COPY --from=builder /web/build/libs/*jar app.jar
# State the port that our application will run on
EXPOSE 8080

# Start Tomcat automatically when the container starts
CMD ["java", "-jar", "app.jar"]