# _Part4 - Containers With Docker + Docker Compose_

## Introduction to Part 3

This report outlines the process of containerizing a web application using Docker.  
The primary objective was to demonstrate how to build, deploy, and manage both a web application and its accompanying database inside Docker containers.

Additionally, an alternative deployment method using **Heroku**—a cloud platform that simplifies application deployment—was explored.

The following aspects are covered in this report:

- Creation of Dockerfiles for the web application and the database
- Configuration of Docker Compose to orchestrate the multi-container environment
- Tagging and pushing Docker images to a remote repository
- Deployment using Heroku for comparison

By completing these steps, I developed a deeper understanding of containerization concepts and modern deployment workflows.

## Table of Contents
- [Dockerfile for the Database Service](#dockerfile-for-the-database-service)
  - [Dockerfile Content](#dockerfile-content)
  - [Explanation of the Dockerfile](#explanation-of-the-dockerfile)
- [Web Application Dockerfile](#web-application-dockerfile)
  - [Dockerfile Content](#dockerfile-content-1)
  - [Explanation of the Dockerfile](#explanation-of-the-dockerfile-1)



## Dockerfile for the Database Service

I began by creating a `Dockerfile` for the database service, which in this case was an **H2 database server**.

The `Dockerfile` was placed in the `db` directory and contains the following content:

### DockerFile Content

```dockerfile

FROM ubuntu:latest

RUN apt-get update && \
    apt-get install -y openjdk-11-jdk-headless && \
    apt-get install unzip -y && \
    apt-get install wget -y

RUN mkdir -p /usr/src/app

WORKDIR /usr/src/app/

RUN wget https://repo1.maven.org/maven2/com/h2database/h2/1.4.200/h2-1.4.200.jar

EXPOSE 8082
EXPOSE 9092

CMD ["java", "-cp", "./h2-1.4.200.jar", "org.h2.tools.Server", "-web", "-webAllowOthers", "-tcp", "-tcpAllowOthers", "-ifNotExists"]
```

### Explanation of the Dockerfile

- **Base Image**: The image uses `ubuntu:latest` as the base to ensure a clean and up-to-date environment.
- **Install Java**: OpenJDK 11 is installed to provide the Java runtime required for running the H2 database. Additional utilities like `unzip` and `wget` are also included.
- **Directory Setup**: A directory at `/usr/src/app` is created to store the application files.
- **Download H2 Database**: The H2 database JAR file is downloaded directly from the Maven repository.
- **Port Exposure**: Ports `8082` (for web access) and `9092` (for TCP access) are exposed to allow external connections.
- **Start Command**: The container runs the H2 database server with parameters that:
    - Enable web and TCP connections,
    - Allow connections from external machines,
    - Create the database if it doesn't already exist.

---

## Web Application Dockerfile

Then, I created a `Dockerfile` in a `web` directory with the following content:

### DockerFile Content

```dockerfile
# Create a basic container with Java 17 and running Tomcat 10
FROM tomcat:10-jdk17-openjdk-slim

# Create a directory for the project and clone the repository there
RUN mkdir -p /usr/src/app
WORKDIR /usr/src/app/

# Update package list and install Git
RUN apt-get update && apt-get install -y git

# Clone the repository and navigate to the project directory
RUN git clone https://github.com/rubenrocha26/devops-24-25-1241924.git

# Navigate to the project directory
WORKDIR /usr/src/app/CA1/Part3/react-and-spring-data-rest-basic

# Change the permissions of the gradlew file to make it executable
RUN chmod +x gradlew

# Run the gradle build command
RUN ./gradlew build

# Copy the generated WAR file to the Tomcat webapps directory
RUN cp ./build/libs/react-and-spring-data-rest-basic-0.0.1-SNAPSHOT.war /usr/local/tomcat/webapps/

# State the port that our application will run on
EXPOSE 8080

# Start Tomcat automatically when the container starts
CMD ["catalina.sh", "run"]
```

### Explanation of the Dockerfile

- **Base Image**: `tomcat:10-jdk17-openjdk-slim` provides a lightweight Tomcat 10 server with OpenJDK 17, ensuring compatibility and efficiency.
- **Directory Setup**: A directory `/usr/src/app` is created to house the application source files.
- **Install Git**: Git is installed to enable the cloning of the project repository directly into the container.
- **Clone Repository**: The application repository is cloned from GitHub into the working directory.
- **Build Project**: The script navigates into the project directory, makes the Gradle wrapper executable, and runs the build using `./gradlew build` to generate the WAR file.
- **Deploy WAR File**: The generated WAR file is copied to the Tomcat `webapps` directory, making it accessible upon server startup.
- **Port Exposure**: Port `8080` is exposed to allow external HTTP traffic to reach the application.
- **Start Command**: Tomcat is started, serving the deployed application automatically.

---


