# _Part 3 – Full Gradle Migration_

## Prerequisites

- Java JDK 17 or higher
- Gradle 7.4.1 or higher
- Node.js 14+ and npm 6+ (for frontend plugin)

---

## Introduction

This document presents a comprehensive overview of the tasks completed in **Part 3 of Class Assignment 1** for the DevOps course in the **SWitCH 24-25** program.  
The main focus of this assignment is the practical application of **Gradle** as a **build automation tool**, continuing the progression from earlier parts by extending its usage to both backend and frontend integration.

A series of structured tasks guide the transition of a **Spring Boot application from Maven to Gradle**, showcasing the flexibility and power of Gradle in managing complex software builds.

This part reinforces prior Gradle experience and deepens the understanding of how to manage **full-stack application builds** within a single, cohesive Gradle project.

---

## Table of Contents

- [Set Up Initial Gradle Project](#set-up-initial-gradle-project)
- [Integrate Existing Code](#integrate-existing-code)
  - [Replace Source Directory](#1-replace-the-source-directory)
  - [Include Additional Configuration Files](#2-include-additional-configuration-files)
  - [Remove Redundant Directories](#3-remove-redundant-directories)
- [Steps to Resolve Compilation Errors](#steps-to-resolve-compilation-errors)
  - [Adjust Import Statements](#1-adjust-import-statements)
  - [Update Package Manager Configuration](#2-update-package-manager-configuration)
  - [Verification and Testing](#3-verification-and-testing)
- [Configure Frontend Plugin for Gradle](#configure-frontend-plugin-for-gradle)
  - [Adding the Plugin](#1-adding-the-plugin)
  - [Configuring the Plugin](#2-configuring-the-plugin)
  - [Updating package.json](#3-updating-packagejson)
  - [Testing the Configuration](#4-testing-the-configuration)
- [Gradle Tasks for File Management](#gradle-tasks-for-file-management)
  - [Task: copyJar](#task-copyjar)
  - [Task: cleanWebpack](#task-cleanwebpack)
- [Alternative Solution](#alternative-solution)
- [Conclusion](#conclusion)

---

## Set Up Initial Gradle Project

The initial setup of the Gradle project involved several key steps to transition from a **Maven-based structure** to a **Gradle-based** one.

To begin, a new **Git branch** was created to isolate the setup and related changes:

```bash
git branch tut-basic-gradle
git checkout tut-basic-gradle
```

Following this, a new **Spring Boot project** was generated using [Spring Initializr](https://start.spring.io/), with the necessary dependencies selected:

- Spring Web (Rest Repositories)
- Thymeleaf
- Spring Data JPA
- H2 Database

These dependencies ensure the application's full functionality, all managed under the Gradle ecosystem.

The generated `.zip` file was downloaded and extracted into the repository under:

```
CA1/Part3/
```

This provided a clean Gradle-based Spring Boot structure, serving as the foundation for the migration.

To verify the setup and explore the available Gradle tasks, the following command was run from the project root:

```bash
./gradlew tasks
```

The output confirmed that Gradle was correctly configured, displaying a wide range of available tasks:

### Application Tasks

- `bootRun` - Run the Spring Boot application.
- `bootTestRun` - Run the application using the test runtime classpath.

### Build Tasks

- `assemble` - Compiles the outputs of the project.
- `bootBuildImage` - Creates an OCI image of the application using the output from the `bootJar` task.
- `bootJar` - Generates an executable jar file containing the main classes and their dependencies.
- `build` - Compiles and tests the project.
- `buildDependents` - Compiles and tests the project along with all projects that depend on it.
- `buildNeeded` - Compiles and tests the project along with all projects it depends on.
- `classes` - Compiles the main classes.
- `clean` - Deletes the build directory.
- `jar` - Generates a jar file containing the classes of the main feature.
- `resolveMainClassName` - Determines the name of the application's main class.
- `resolveTestMainClassName` - Determines the name of the main test class.
- `testClasses` - Compiles the test classes.

### Build Setup Tasks

- `init` - Initializes a new Gradle build.
- `wrapper` - Generates the Gradle wrapper files.

### Documentation Tasks

- `javadoc` - Generates JavaDoc for the main classes.

### Help & Insight Tasks

- `buildEnvironment` - Displays all buildscript dependencies declared in the root project `react-and-spring-data-rest-basic`.
- `dependencies` - Lists all dependencies declared in the root project.
- `dependencyInsight` - Provides insight into a specific dependency in the project.
- `dependencyManagement` - Displays the dependency management configuration for the project.
- `help` - Shows a help message.
- `javaToolchains` - Displays the detected Java toolchains.
- `outgoingVariants` - Lists the outgoing variants of the project.
- `projects` - Lists the sub-projects of the root project.
- `properties` - Displays the properties of the project.
- `resolvableConfigurations` - Lists the configurations that can be resolved.
- `tasks` - Shows all runnable tasks from the root project.

This validated that the Gradle project was properly initialized and ready for customization and further development. 
The setup created a robust foundation for the tasks ahead, allowing full control over the build process using Gradle.

---

## Integrate Existing Code

In this phase, the project involved incorporating the pre-existing codebase from a basic tutorial setup into the newly structured Gradle environment. 
The integration process was carried out carefully to ensure all components function seamlessly under the new build management system.

### 1. Replace the Source Directory
The original `src` directory in the Gradle project was removed to accommodate the established codebase. 
The entire `src` folder, along with its subdirectories, was copied from the tutorial setup into the new Gradle project directory.

### 2. Include Additional Configuration Files
Essential configuration files, such as `webpack.config.js` and `package.json`, were also copied into the root directory of the new project. 
This step ensures that the frontend build configurations and dependencies remain intact.

### 3. Remove Redundant Directories
After migrating the code, the `src/main/resources/static/built` directory was deleted. 
This directory is meant to be automatically generated by Webpack during the build process and should not be included in version control to avoid conflicts and redundancy.

## Steps to Resolve Compilation Errors

### 1. Adjust Import Statements
To align with updated project dependencies and the transition from Java EE to Jakarta EE, modifications were made to the Java classes. 
For example, in the `Employee.java` class, import statements were updated from `javax.persistence` to `jakarta.persistence`.

### 2. Update Package Manager Configuration
The `package.json` file was updated to specify a fixed version of the package manager by adding the line `"packageManager": "npm@9.6.7"`. 
This change ensures that the project uses a consistent version of the package manager across different environments.

### 3. Verification and Testing

After integrating the code and applying the necessary configuration adjustments, the application was tested to ensure its operational integrity:

- **Running the Application**: The command `./gradlew bootRun` was executed to compile and launch the backend.
- **Verifying the Frontend**: Accessing [http://localhost:8080](http://localhost:8080) in a web browser displayed an empty page, which is expected at this stage. This occurs because the current Gradle setup lacks the plugin needed to handle the frontend code—a gap that will be addressed in subsequent steps.

This methodical approach ensures that the core codebase is seamlessly integrated into the Gradle framework, setting the stage for further enhancements and the incorporation of more complex functionalities.

## Configure Frontend Plugin for Gradle

To streamline the build process for the frontend under the new Gradle system, the project incorporated the `org.siouan.frontend-gradle-plugin`. 
This plugin functions similarly to the `frontend-maven-plugin` used in Maven, efficiently managing frontend assets within a Gradle-managed environment.

### 1. Adding the Plugin

The Gradle build script was updated to include the appropriate frontend plugin for the Java version used in the project. For Java 17, the following line was added to the plugins block in `build.gradle`:

```groovy
id "org.siouan.frontend-jdk17" version "8.0.0"
```

### 2. Configuring the plugin

To ensure proper handling of frontend assets, specific configurations were added to `build.gradle`. 
These settings specify the Node.js version to be used and define the commands for assembling, cleaning, and checking the frontend:

```groovy
frontend {
    nodeVersion = "16.20.2"
    assembleScript = "run build"
    cleanScript = "run clean"
    checkScript = "run check"
}
```

### 3. Updating package.json

The `package.json` file was updated to include the necessary scripts for managing Webpack and other frontend-related tasks:

```json
"scripts":{
    "webpack": "webpack",
    "build": "npm run webpack",
    "check": "echo Checking frontend",
    "clean": "echo Cleaning frontend",
    "lint": "echo Linting frontend",
    "test": "echo Testing frontend"
}
```

### 4. Testing the Configuration

After configuring the frontend plugin, the following tests were performed to ensure everything worked as expected:

- **Build Test**: Running `./gradlew build` confirmed that the project builds successfully with the integrated frontend configuration.
- **Application Execution**: Executing `./gradlew bootRun` started the application, and accessing [http://localhost:8080](http://localhost:8080) in a web browser displayed the expected frontend content. This verified that the Gradle plugin correctly managed the frontend resources during both build and runtime.

This configuration effectively integrates frontend build management into the Gradle environment, enhancing the project's ability to handle complex full-stack development workflows.

---

## Gradle Tasks for File Management

To enhance the management of the project's files, specifically focusing on the distribution and cleanup processes, two custom Gradle tasks were defined: **copyJar** and **cleanWebpack**.

### Task: copyJar

**Objective:**  
This task is responsible for copying the `.jar` file generated by the `bootJar` task directly from the output directory to a `dist` folder at the project root. This approach ensures that only the correct, fully assembled `.jar` file is targeted for distribution, minimizing errors and ensuring that deployments contain the most current build.

**Configuration:**

```groovy
task copyJar(type: Copy) {
    dependsOn bootJar
    from bootJar.outputs
    into file("dist")
}
```

**Dependencies:**
It explicitly depends on the `bootJar` task, ensuring that the copy operation is executed only after `bootJar` has successfully completed, thereby maintaining a clear and reliable build dependency.

### Task: cleanWebpack

**Objective:**  
This task is designed to remove all files generated by Webpack from the `src/main/resources/static/built` directory. 
Its purpose is to keep the build environment clean by ensuring that only the necessary files are present in each build, thereby preventing issues that might arise from outdated or redundant files.

**Configuration:**

```groovy
task cleanWebpack(type: Delete) {
    delete 'src/main/resources/static/built'
}
clean.dependsOn cleanWebpack
```

**Dependencies:**
This task is configured to automatically run before the standard Gradle `clean task, integrating it into the regular cleanup process.

### Verification of Task Functionality

Both custom tasks were executed to confirm their proper operation:

### Running the copyJar Task

- **Command:**
```bash
./gradlew copyJar
```

- **Result:**
The `.jar` file generated by the `bootJar` task was successfully copied to the dist directory. 
This outcome verifies that the task correctly identifies and transfers the proper artifact, thereby confirming its effective integration into the build process and its suitability for subsequent distribution stages.


### Running the cleanWebpack Task

- **Command:**
```bash
./gradlew cleanWebpack
```

- **Result:**
All files in the `src/main/resources/static/built directory were successfully deleted. 
This confirms that the cleanup task functions as intended, ensuring that the build environment remains free of outdated or unnecessary files.

These tasks have been seamlessly integrated into the build process to automate file management. 
Their proper execution enhances the overall efficiency and reliability of both the build and deployment processes, contributing to streamlined project maintenance and management.

## Alternative Solution