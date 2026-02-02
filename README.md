Online Shop — Full Stack E-commerce Application

This is a modern web application for an online store, built with a robust architecture using Java (Spring Boot), React.js, and PostgreSQL, all containerized with Docker.

Architecture Diagram

The system follows a classic Client-Server-Database pattern within a containerized environment:

     graph TD
        User((User/Browser)) <--> |HTTP/REST| Frontend[React.js Container]
        Frontend <--> |API Calls| Backend[Spring Boot Container]
        Backend <--> |JDBC/JPA| DB[(PostgreSQL Container)]
    
    subgraph "Docker Network"
        Frontend
        Backend
        DB
        end

Tech Stack

    Frontend: React.js (Hooks, Axios, Styled Components / CSS Modules)
    Backend: Java 21, Spring Boot (Spring Data JPA, Hibernate)
    Database: PostgreSQL
    Containerization: Docker, Docker Compose
    Build Tools: Maven, Lombok (Backend), NPM (Frontend)

Getting Started (Docker Deployment)

The easiest way to run the project is using Docker Compose.

  1. Clone the repository:
     
    git clone https://github.com/mtrxxp/Online_Shop.git
    cd Online_Shop
  3. Run the entire stack:
     
    docker-compose up --build

Access the application:

    Frontend UI: http://localhost:3000
    Backend API: http://localhost:8080
    Database: localhost:5432

Local Development:
Backend (Spring Boot):
  
    Navigate to the backend directory.
    Update src/main/resources/application.properties with your local Postgres credentials.
    Run: ./mvnw spring-boot:run

  Frontend (React):

    Navigate to the frontend directory.
    Install dependencies: npm install
    Run: npm start

  
