# Sales Metrics API

![Java](https://img.shields.io/badge/Java-17-blue.svg)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.3-brightgreen.svg)
![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)

A Spring Boot REST API for tracking sales outreach activities and metrics.

## 📚 Table of Contents

- [Project Overview](#project-overview)
- [Current Implementation Status](#current-implementation-status)
- [Architecture](#architecture)
- [Database Schema](#database-schema)
- [Setup Instructions](#setup-instructions)
- [API Testing](#api-testing)
- [Sample Data](#sample-data)
- [Known Issues & Next Steps](#known-issues--next-steps)
- [Technology Stack](#technology-stack)
- [Project Structure](#project-structure)
- [Development Notes](#development-notes)
- [Git Workflow](#git-workflow)
- [Review Questions](#review-questions)
- [Contact](#contact)

## Project Overview

This application allows users to track their sales outreach activities (emails, calls, meetings) and provides metrics on their performance. Built as a portfolio project to demonstrate Spring Boot, JPA, and REST API development skills.

## Current Implementation Status

### ✅ Completed Features
- **Project Setup**: Spring Boot application with Maven
- **Entity Models**: User and OutreachAction entities with JPA relationships
- **Repository Layer**: Spring Data JPA repositories
- **Service Layer**: Business logic for managing outreach actions
- **REST API**:
  - `POST /api/actions` – create outreach actions
  - `GET /api/actions` – retrieve all actions
  - `GET /api/actions?userId={id}` – retrieve actions filtered by user
- **DTOs**: OutreachActionDTO created for clean API responses
- **Data Seeding**: Sample data loaded on startup for testing

## Architecture

```

├── Controllers (REST endpoints)
├── Services (Business logic)
├── Repositories (Data access)
├── Models/Entities (JPA entities)
├── DTOs (Data transfer objects)
└── Configuration (Spring Boot config)

````

## Database Schema

**Users Table**
- id (Long, auto-generated)
- name (String)
- email (String)

**Outreach Actions Table**
- id (Long, auto-generated)
- user_id (Foreign key to Users)
- type (Enum: EMAIL, CONNECTION, RESPONSE, MEETING)
- date (LocalDate)
- notes (String)

## Setup Instructions

### Prerequisites
- Java 17 or higher
- Maven 3.6+
- IDE with Lombok support (IntelliJ IDEA, VS Code with Lombok extension, etc.)

### Running the Application

1. **Clone the repository:**
   ```bash
   git clone https://github.com/MJCarvajalQ/sales-metrics-api.git
   cd sales-metrics-api

2. **Run the application:**

   ```bash
   mvn spring-boot:run
   ```

3. **Access the application:**

    * API Base URL: `http://localhost:8080`
    * H2 Database Console: `http://localhost:8080/h2-console`

        * JDBC URL: `jdbc:h2:mem:testdb`
        * Username: `sa`
        * Password: (leave empty)

ℹ️ You can read more about [H2 Console usage here](https://www.h2database.com/html/main.html).

## API Testing

### Create Outreach Action

```bash
curl -X POST http://localhost:8080/api/actions \
  -H "Content-Type: application/json" \
  -d '{
    "userId": 1,
    "type": "EMAIL",
    "date": "2025-07-04",
    "notes": "Follow-up email sent to prospect"
  }'
```

### Sample Response

```json
{
  "id": 3,
  "user": {
    "id": 1,
    "name": "Majo",
    "email": "majo@example.com"
  },
  "type": "EMAIL",
  "date": "2025-07-04",
  "notes": "Follow-up email sent to prospect"
}
```

### Retrieve All Outreach Actions

```bash
curl http://localhost:8080/api/actions
```

Returns all outreach actions in the system.

### Filter Outreach Actions by User

```bash
curl "http://localhost:8080/api/actions?userId=1"
```

Returns only the actions performed by the user with ID `1`.

#### Sample Response:

```json
[
  {
    "userId": 1,
    "type": "EMAIL",
    "date": "2025-07-08",
    "notes": "Initial outreach"
  },
  {
    "userId": 1,
    "type": "MEETING",
    "date": "2025-07-09",
    "notes": "Demo scheduled"
  }
]
```

> ℹ️ If no `userId` is provided, all actions are returned.
> If the user ID doesn’t exist, an empty array `[]` is returned.

## Sample Data

The application loads test data on startup:

* **Users**: Majo ([majo@example.com](mailto:majo@example.com)), Pedro ([pedro@example.com](mailto:pedro@example.com))
* **Actions**: Sample email and meeting entries

## Known Issues & Next Steps

### ✅ Completed

* GET endpoints for retrieving actions
* Filtering by user ID

### 🚀 Planned Features

* 📅 Date range filtering for actions
* 📊 Metrics calculations (actions per day, success rates)
* 🔐 User authentication and authorization
* 📥 Export actions/metrics to Excel (Apache POI)
* 🌐 Swagger/OpenAPI documentation
* 🧪 Unit and integration testing
* 🧭 Frontend dashboard (React/Angular)

## Technology Stack

* **Backend**: Spring Boot 3.5.3
* **Database**: H2 (development), PostgreSQL (production planned)
* **ORM**: Spring Data JPA
* **Build Tool**: Maven
* **Java Version**: 17
* **Additional**: Lombok (code generation), Spring Boot Validation

## Project Structure

```
src/main/java/com/mjcarvajalq/sales_metrics_api/
├── SalesMetricsApiApplication.java (Main application + data seeding)
├── controllers/
│   └── OutreachActionController.java
├── services/
│   ├── OutreachActionService.java (Interface)
│   └── OutreachActionServiceImpl.java (Implementation)
├── repositories/
│   ├── UserRepository.java
│   └── OutreachActionRepository.java
├── dto/
│   └── OutreachActionDTO.java
└── model/
    ├── User.java
    ├── OutreachAction.java
    └── ActionType.java (Enum)
```

## Development Notes

### Design Decisions

* Used H2 for development simplicity and easy testing
* Implemented CommandLineRunner for data seeding
* Chose simple entity relationships to start
* REST-first approach for API design

### Code Quality

* Following Spring Boot conventions
* Clean separation of concerns
* Proper HTTP status codes
* JSON request/response handling

## Git Workflow

* Feature branches created for each task (`feature/post-outreach-actions`, `feature/get-all-actions`, etc.)
* Pull Requests opened into `main` for review
* Commits follow conventional messages for clarity (e.g., `feat: implement GET /api/actions with userId filter`)

## Review Questions

1. **Architecture**: Is the current layered architecture appropriate for the project scope?
2. **Entity Design**: Are the User and OutreachAction entities well-designed?
3. **API Design**: Do the endpoints follow REST best practices?
4. **Error Handling**: What error handling improvements would you recommend?
5. **Testing**: What testing strategy would you suggest for this application?

## Contact

**Developer**: MJ Carvajal
**GitHub**: [MJCarvajalQ](https://github.com/MJCarvajalQ)
**Project**: Portfolio piece demonstrating Spring Boot API development

---

*This project is part of my software development portfolio. Feedback and suggestions are welcome!*

