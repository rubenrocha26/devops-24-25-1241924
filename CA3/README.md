# _CI/CD Pipelines with Jenkins_

## Introduction to CA3

The objective of this assignment was to configure CI/CD pipelines using **Jenkins** for two applications developed in previous coursework. These include:

- The **Gradle Basic Demo Application** (from CA1 - Part 2)
- The **React and Spring Data REST Basic Application** (from CA1 - Part 3)

This practical assignment aims to deepen the understanding of Continuous Integration and Continuous Deployment (CI/CD) principles by applying them in a real-world context. 
The focus is on automating key stages of the software lifecycle—**build**, **test**, and **deployment**—using Jenkins as the orchestration tool.

This document provides a step-by-step overview of the pipeline setup and configuration. 
It is intended as a comprehensive guide, demonstrating how Jenkins can be used to streamline and automate the delivery workflow for both frontend and backend applications.


## Table of Contents
- [Setting Up](#setting-up)
- [Jenkins Pipeline for Gradle Basic Demo](#jenkins-pipeline-for-gradle-basic-demo)
  - [Pipeline Stages Overview](#pipeline-stages-overview)
  - [Creating the Pipeline Job in Jenkins](#creating-the-pipeline-job-in-jenkins)
  - [Executing the Pipeline](#executing-the-pipeline)


---

## Setting Up

To successfully configure Jenkins and implement the CI/CD pipeline, it was necessary to install and verify the following tools:

- **Git**: Used for version control and repository management. The installation was verified by executing the command `git --version`.

- **Docker**: Required for building and running containerized applications. The Docker installation was confirmed with the command `docker --version`.

- **Jenkins**: Served as the core automation server for orchestrating the CI/CD pipeline. 
Jenkins was installed locally, and its successful setup was verified by accessing the Jenkins dashboard through a web browser.

---

## Jenkins Pipeline for Gradle Basic Demo

The initial stage of the CA3 assignment focused on configuring a Jenkins pipeline for the **Gradle Basic Demo Application**, previously developed in CA1/Part2.

The pipeline was defined through a `Jenkinsfile` placed inside CA3/gradle_basic_demo directory. This file outlines the steps Jenkins must follow to execute the CI/CD workflow for this application.

Below is the content of the `Jenkinsfile` used:

```groovy
pipeline {
    agent any

    tools{
        jdk 'JDK17'
    }

    environment{
        JAVA_HOME = tool 'JDK17'
        PATH = "${JAVA_HOME}/bin:${env.PATH}"
    }

    stages {
        stage('Checkout') {
            steps {
                echo 'Checking out from repository'
                git branch: 'main', url: 'https://github.com/rubenrocha26/devops-24-25-1241924.git'
            }
        }
        stage('Assemble') {
            steps {
                dir('CA1/Part2/gradle-basic') {
                    echo 'Assembling...'
                    sh 'chmod +x gradlew'
                    sh './gradlew clean assemble'
                }
            }
        }
        stage('Test') {
            steps {
                dir('CA1/Part2/gradle-basic') {
                    echo 'Running Tests...'
                    sh './gradlew test'
                    junit 'build/test-results/test/*.xml'
                }
            }
        }
        stage('Archive') {
            steps {
                dir('CA1/Part2/gradle-basic') {
                    echo 'Archiving artifacts...'
                    archiveArtifacts artifacts: 'build/libs/*.jar', allowEmptyArchive: true
                }
            }
        }
    }
}
```

### Pipeline Stages Overview

The `Jenkinsfile` for the Gradle Basic Demo Application defined a pipeline composed of four sequential stages:

1. **Checkout**  
   In this initial stage, the project source code was retrieved from the corresponding GitHub repository using the Git plugin.

2. **Assemble**  
   This stage executed the command `./gradlew clean assemble` to clean the project and compile all necessary artifacts.

3. **Test**  
   The test suite for the Gradle project was executed with `./gradlew test`, ensuring that the application behaved as expected before proceeding to the next step.

4. **Archive**  
   Finally, the generated `.jar` file was archived as a build artifact. This allows future retrieval and deployment without requiring a rebuild.

### Creating the Pipeline Job in Jenkins

To create a new pipeline job in Jenkins, the following steps were performed:

1. Accessed the Jenkins dashboard and clicked on **"New Item"**.
2. Provided a name for the pipeline job and selected the **"Pipeline"** option.
3. Under the **Pipeline** configuration section, selected **"Pipeline script from SCM"** as the definition source.
4. Chose **Git** as the Source Code Management (SCM) option.
5. Entered the repository URL where the project is hosted.
6. Specified `main` as the branch to track.
7. Set the **Script Path** to `CA3/gradle_basic_demo/Jenkinsfile` to point Jenkins to the location of the pipeline definition within the repository.
8. Saved the configuration to finalize the job setup.

### Executing the Pipeline

Once the pipeline job was configured, the build process was initiated by clicking on **"Build Now"**. 
The progress and output of each stage were monitored through the **Jenkins Console Output**, which provided real-time feedback on the execution.

Below is a screenshot illustrating the Jenkins pipeline job after a successful build:

[!buildSuccessPart1](images/buildSuccessPart1.png)

---



