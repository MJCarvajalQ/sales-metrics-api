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
- dateTime (LocalDateTime)
- notes (String)

## Setup Instructions

### Prerequisites
- Java 17 or higher
- Maven 3.6+
- Docker and Docker Compose (for local development)
- PostgreSQL 12+ (for production deployment)
- IDE with Lombok support (IntelliJ IDEA, VS Code with Lombok extension, etc.)

### Running the Application

1. **Clone the repository:**
   ```bash
   git clone https://github.com/MJCarvajalQ/sales-metrics-api.git
   cd sales-metrics-api
   ```

2. **Set up PostgreSQL database using Docker Compose (Recommended for local development):**
   
   Start PostgreSQL and pgAdmin using Docker Compose:
   ```bash
   docker-compose up -d
   ```
   
   This will start:
   - PostgreSQL 15 on port `5432`
   - pgAdmin 4 on port `5050` (optional database management tool)
   
   The database is automatically configured with:
   - Database: `sales_metrics_dev`
   - User: `sales_user`
   - Password: `sales_password`
   - Data persists in Docker volumes between restarts
   
   **Optional: Access pgAdmin**
   - Open http://localhost:5050
   - Login with:
     - Email: `admin@salesmetrics.local`
     - Password: `admin`
   - Add a new server with:
     - Host: `postgres` (container name)
     - Port: `5432`
     - Database: `sales_metrics_dev`
     - Username: `sales_user`
     - Password: `sales_password`
   
   **Stop the database:**
   ```bash
   docker-compose down
   ```
   
   **Stop and remove volumes (clean slate):**
   ```bash
   docker-compose down -v
   ```
   
   **Alternative: Manual PostgreSQL setup**
   
   If you prefer not to use Docker, you can set up PostgreSQL manually:
   ```sql
   CREATE DATABASE sales_metrics_dev;
   CREATE USER sales_user WITH ENCRYPTED PASSWORD 'sales_password';
   GRANT ALL PRIVILEGES ON DATABASE sales_metrics_dev TO sales_user;
   ```

3. **Run the application:**
   
   **Development (with defaults - works with Docker Compose):**
   ```bash
   mvn spring-boot:run -Dspring-boot.run.profiles=dev
   ```
   
   The application will automatically connect to the Docker Compose PostgreSQL instance.
   
   **Production (requires environment variables):**
   ```bash
   mvn spring-boot:run -Dspring-boot.run.profiles=prod
   ```

4. **Access the application:**
   * API Base URL: `http://localhost:8080`

### Environment Variables

The application uses environment variables for database configuration. Different profiles use different strategies:

#### Development Profile (`dev`)
- Uses environment variables with safe defaults
- Default database: `sales_metrics_dev`
- Default credentials: `sales_user` / `sales_password`
- Data seeding is enabled by default

#### Production Profile (`prod`)
- **REQUIRES** all environment variables to be set
- No default values for security
- Must set: `DB_URL`, `DB_USERNAME`, `DB_PASSWORD`
- Data seeding is disabled

#### Required Environment Variables

| Variable | Description | Required in Prod | Default (Dev) |
|----------|-------------|------------------|---------------|
| `DB_URL` | PostgreSQL database URL | ✅ Yes | `jdbc:postgresql://localhost:5432/sales_metrics_dev` |
| `DB_USERNAME` | Database username | ✅ Yes | `sales_user` |
| `DB_PASSWORD` | Database password | ✅ Yes | `sales_password` |

#### Setting Environment Variables

**Option 1: Export in terminal (macOS/Linux)**
```bash
export DB_URL=jdbc:postgresql://localhost:5432/sales_metrics_dev
export DB_USERNAME=your_username
export DB_PASSWORD=your_password
```

**Option 2: IntelliJ Run Configuration**
1. Run → Edit Configurations
2. Select your Spring Boot configuration
3. Environment variables → Add:
   - `DB_URL=jdbc:postgresql://localhost:5432/sales_metrics_dev`
   - `DB_USERNAME=your_username`
   - `DB_PASSWORD=your_password`
4. Active profiles → Add: `dev` or `prod`

**Option 3: Create `.env` file (IntelliJ built-in support)**
Create a `.env` file in the project root (already in `.gitignore`):
```
DB_URL=jdbc:postgresql://localhost:5432/sales_metrics_dev
DB_USERNAME=your_username
DB_PASSWORD=your_password
```

#### Running with Profiles

**Development:**
```bash
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

**Production:**
```bash
mvn spring-boot:run -Dspring-boot.run.profiles=prod
```

### Testing

**Unit/Integration Tests:**
Tests use H2 in-memory database and don't require PostgreSQL setup:
```bash
mvn test
```

**Docker Compose Testing:**

**Automated Testing (Recommended):**
Run the automated test script to verify everything works:
```bash
./test-docker-connection.sh
```

This script will:
- Verify Docker and Docker Compose are installed
- Start Docker Compose services
- Check PostgreSQL container is running
- Test database connection
- Verify database and user exist
- Test data persistence across restarts
- Verify pgAdmin service

**Manual Testing:**
If you prefer to test manually, follow these steps:

1. **Start Docker Compose:**
   ```bash
   docker-compose up -d
   # Or with Docker Compose v2:
   docker compose up -d
   ```

2. **Verify PostgreSQL is running:**
   ```bash
   docker-compose ps
   # Or:
   docker compose ps
   ```
   You should see both `postgres` and `pgadmin` services running.

3. **Check PostgreSQL logs:**
   ```bash
   docker-compose logs postgres
   ```
   Look for "database system is ready to accept connections".

4. **Test database connection:**
   ```bash
   docker-compose exec postgres psql -U sales_user -d sales_metrics_dev -c "SELECT version();"
   ```
   This should display the PostgreSQL version.

5. **Run the application and verify connection:**
   ```bash
   mvn spring-boot:run -Dspring-boot.run.profiles=dev
   ```
   The application should:
   - Connect successfully to the Docker Compose PostgreSQL instance
   - Show no connection errors in logs
   - Start Liquibase migrations automatically
   - Seed initial data (if enabled)
   
   **Verify in application logs:**
   - Look for "Liquibase changelog" messages
   - Check for "Test users and actions seeded successfully" (if seeding enabled)
   - Ensure no database connection errors

6. **Test data persistence:**
   - Start the application and create some data via API
   - Stop the application: `Ctrl+C`
   - Stop Docker Compose: `docker-compose down`
   - Start Docker Compose again: `docker-compose up -d`
   - Start the application again
   - Verify data is still there (use API to retrieve it)

7. **Access pgAdmin (optional):**
   - Open http://localhost:5050
   - Login with:
     - Email: `admin@salesmetrics.local`
     - Password: `admin`
   - Add server:
     - Host: `postgres`
     - Port: `5432`
     - Database: `sales_metrics_dev`
     - Username: `sales_user`
     - Password: `sales_password`
   - Verify you can see the database and tables created by Liquibase

## API Testing

### Create Outreach Action

```bash
curl -X POST http://localhost:8080/api/actions \
  -H "Content-Type: application/json" \
  -d '{
    "userId": 1,
    "type": "EMAIL",
    "dateTime": "2025-07-04T10:30:00",
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
  "dateTime": "2025-07-04T10:30:00",
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
    "dateTime": "2025-07-08T09:15:00",
    "notes": "Initial outreach"
  },
  {
    "userId": 1,
    "type": "MEETING",
    "dateTime": "2025-07-09T14:00:00",
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
* **Database**: PostgreSQL (production), H2 (testing)
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

