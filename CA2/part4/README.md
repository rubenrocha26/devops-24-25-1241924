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
- [Docker Compose](#docker-compose)
  - [Docker Compose file Content](#docker-compose-file-content)
  - [Explanation](#explanation)
  - [Building and Running the Services](#building-and-running-the-services)



## Dockerfile for the Database Service

I began by creating a `Dockerfile` for the database service, which in this case was an **H2 database server**.

The `Dockerfile` was placed in the `db` directory and contains the following content:

### DockerFile Content

```dockerfile
FROM amazoncorretto:17-alpine

WORKDIR /h2

RUN apk add --no-cache wget && \
    wget https://repo1.maven.org/maven2/com/h2database/h2/1.4.200/h2-1.4.200.jar && \
    apk del wget

EXPOSE 8082 9092

CMD ["java", "-cp", "h2-1.4.200.jar", "org.h2.tools.Server", "-web", "-webAllowOthers", "-tcp", "-tcpAllowOthers", "-ifNotExists"]
```

### Explanation of the Dockerfile

- **Base Image**: The image uses `amazoncorretto` as the base to ensure a clean and up-to-date environment.
- **Directory Setup**: A directory `/h2` is created to store the application files.
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
```

### Explanation of the Dockerfile

- **Base Image**: `eclipse-temurin:17-jdk` provides a lightweight Tomcat 10 server with OpenJDK 17, ensuring compatibility and efficiency.
- **Directory Setup**: A directory `/web` is created to house the application source files.
- **Copy Repository**: The application repository is copied from `MyMachine` into the working directory.
- **Build Project**: The script navigates into the project directory, makes the Gradle wrapper executable, and runs the build using `./gradlew build` to generate the Jar file.
- **Deploy Jar File**: The generated JAr file is copied to the Tomcat `webapps` directory, making it accessible upon server startup.
- **Port Exposure**: Port `8080` is exposed to allow external HTTP traffic to reach the application.
- **Start Command**: Tomcat is started, serving the deployed application automatically.

---

## Docker Compose

To manage both the database and the web application containers, I created a `docker-compose.yml` file.  
This file defines the two services—**db** and **web**—and specifies how they interact with each other, including networking, port mapping, and build context.  
Using Docker Compose simplifies the orchestration process by allowing both containers to be launched and managed together with a single command.

### Docker Compose file Content

```dockerfile
version: '3.8'

services:

  db:
    build: ./db
    ports:
      - "8082:8082"
      - "9092:9092"
    volumes:
      - ./data:/usr/src/data-backup
    networks:
      my_own_network:
        ipv4_address: 192.168.56.11

  web:
    build:
      context: ../../
      dockerfile: CA2/Part4/web/Dockerfile
    ports:
      - "8080:8080"
    networks:
      - my_own_network
    depends_on:
      - db

networks:
  my_own_network:
    driver: bridge
    ipam:
      config:
          - subnet: "192.168.56.0/24"
```

### Explanation

- **Version**:  
  The `docker-compose.yml` file uses version `3.8` to ensure compatibility with the features utilized.

- **Services**:

- **DB Service**:
  - Built from the `./db` directory.
  - Maps container ports `8082` (web interface) and `9092` (TCP server) to the host.
  - A volume is mounted to persist database data across restarts.
  - Connected to `my_custom_network` with a static IP `192.168.56.11`.

- **Web Service**:
  - Built from the `./web` directory.
  - Maps container port `8080` to the host for web access.
  - The service is also connected to the custom network `my_own_network`, allowing it to communicate securely with the database service.
  - Depends on the `db` service, ensuring the database starts before the web application.

- **Networks**:
- **my_custom_network**:
  A custom network `my_own_network` is defined with a subnet `192.168.56.0/24`, ensuring both services can communicate with each other within a dedicated network space. 
- The bridge driver is used for network isolation between containers.

### Building and Running the Services

To build and run the services defined in the `docker-compose.yml` file, I executed the following command:

```bash
docker-compose up --build
```






