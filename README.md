# SpringBootThymeleaf — Project Management System

A simple Project Management System built with Spring Boot and Thymeleaf. It lets you manage:
- Priorities (e.g., High, Medium, Low)
- Statuses (e.g., To Do, In Progress, Done)
- Projects
- Tasks (linked to Projects and Statuses)

The UI uses Thymeleaf templates and Bootstrap (via WebJars). Data is stored in PostgresSQL using Spring Data JPA (Hibernate).

## Table of Contents
- Features
- Tech Stack
- Prerequisites
- Getting Started
  - Database setup (PostgresSQL)
  - Run the app
- Useful URLs
- Running Tests
- Build a JAR
- Project Structure
- Configuration

## Features
- CRUD for Priorities, Statuses, Projects, and Tasks
- Search:
  - Projects by name and priority type
  - Tasks by name, status type, and project name
- Server-side validation for forms
- Simple Bootstrap-based UI

## Tech Stack
- Java 21
- Spring Boot 3.5.4
  - Web, Thymeleaf, Data JPA, Validation
- PostgresSQL (runtime)
- Hibernate (DDL auto-update)
- Bootstrap 5 (WebJars)
- Maven (build)

## Prerequisites
- Java 21
- Maven 3.9+
- PostgresSQL 14+ running locally

## Getting Started

### 1) Database setup (PostgresSQL)
The application expects a PostgresSQL database named `pms` on localhost with username and password `postgres`/`postgres`.

Default configuration (src/main/resources/application.properties):
```
spring.datasource.url=jdbc:postgresql://localhost:5432/pms
spring.datasource.username=postgres
spring.datasource.password=postgres
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgresPlusDialect
spring.jpa.properties.hibernate.hbm2ddl.auto=update
```

Create the database and a user if needed:
```
psql -U postgres -h localhost -c "CREATE DATABASE pms;"
# Optional: if you use a custom user/password, also grant privileges and update application.properties accordingly
```

Alternatively, a quick Docker command (example):
```
docker run --name pms-postgres -e POSTGRES_PASSWORD=postgres -e POSTGRES_USER=postgres -e POSTGRES_DB=pms -p 5432:5432 -d postgres:16
```

### 2) Run the app
From the project root:
```
mvn spring-boot:run
```
The app starts on http://localhost:8080 by default.

If you prefer running the packaged JAR, see the Build a JAR section below.

## Useful URLs
- Home: http://localhost:8080/
- Priorities: 
  - List: GET /priorities
  - Add form: GET /priorities/showPriority
- Statuses:
  - List: GET /statuses
  - Add form: GET /statuses/showStatus
- Projects:
  - List: GET /projects
  - Add form: GET /projects/showProject
  - Search by name: GET /projects/search/projectName?projectName=...
  - Search by priority: GET /projects/search/priorityType?priorityType=...
- Tasks:
  - List: GET /tasks
  - Add form: GET /tasks/showTask
  - Search by task name: GET /tasks/search/taskName?taskName=...
  - Search by status: GET /tasks/search/statusType?statusType=...
  - Search by project name: GET /tasks/search/projectName?projectName=...

Note: Edit and delete actions are available from the respective list/detail screens in the UI.

## Running Tests
Execute all tests:
```
mvn test
```

## Build a JAR
Create an executable JAR:
```
mvn clean package
```
Run it:
```
java -jar target/SpringBootThymeleaf-0.0.1-SNAPSHOT.jar
```

## Project Structure
- src/main/java/org/chintanpatel/springbootthymeleaf
  - priority/… (entity, repository, service, controller)
  - status/… (entity, repository, service, controller)
  - project/… (entity, repository, service, controller)
  - task/… (entity, repository, service, controller)
  - SpringBootThymeleafApplication.java (entry point)
- src/main/resources
  - templates/… (Thymeleaf pages: index, and CRUD views for each module)
  - application.properties
- src/test/java/… (controller tests)

## Configuration
Adjust application settings in `src/main/resources/application.properties`.
- To change the server port:
```
server.port=8081
```
- To point to a different PostgresSQL instance, update the datasource URL, username, and password.

If you change database credentials or name, ensure the user has privileges and the database exists before running the app.

---

Feel free to improve the UI, add authentication, or extend the domain model as needed. Happy coding!