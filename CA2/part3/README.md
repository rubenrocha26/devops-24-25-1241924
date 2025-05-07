# _Part3 - Containers With Docker_

## Introduction to Part 3

This project focuses on gaining hands-on experience with Docker by containerizing a chat application.  
The chat server was originally developed as part of the CA2 coursework and is hosted in a Bitbucket repository.

This project explores two approaches to containerizing the application:

1. **Building the chat server within the Dockerfile**
2. **Compiling the chat server on the host machine and copying the JAR file into the Docker image**

By creating Docker images and running containers, the goal is to ensure the chat server runs consistently across different environments.


## Table of Contents
- [Environment Setup](#environment-setup)


---

## Environment Setup

To set up a development environment using Docker, follow these steps:

1. **Install Docker**  
   Download and install Docker Desktop from the [official Docker website](https://www.docker.com/products/docker-desktop). Follow the installation instructions for your operating system.

2. **Verify Installation**  
   After installation, verify that Docker is installed and running by executing the following command:
```bash
   docker --version
```