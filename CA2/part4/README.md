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