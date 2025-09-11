# Database Setup Documentation

## Overview
This document provides instructions for setting up the PostgreSQL database for the Sales Metrics API, including configuration and integration with Liquibase.

## Prerequisites
- PostgreSQL 13 or higher
- Administrative access to a PostgreSQL instance

## Installation Steps

### 1. Install PostgreSQL

Follow your operating system's installation instructions:
- **MacOS**: Using Homebrew
  ```bash
  brew install postgresql
  brew services start postgresql
  ```

- **Ubuntu**:
  ```bash
  sudo apt update
  sudo apt install postgresql postgresql-contrib
  sudo systemctl start postgresql
  ```

- **Windows**:
  
  Download and install from [PostgreSQL official website](https://www.postgresql.org/download/windows/).

### 2. Set up Database and User

1. Access the PostgreSQL command line:
    ```bash
    psql postgres
    ```

2. Create a new user:
    ```sql
    CREATE USER sales_metrics_user WITH PASSWORD 'securepassword';
    ALTER ROLE sales_metrics_user SET client_encoding TO 'utf8';
    ALTER ROLE sales_metrics_user SET default_transaction_isolation TO 'read committed';
    ALTER ROLE sales_metrics_user SET timezone TO 'UTC';
    ```

3. Create the database:
    ```sql
    CREATE DATABASE sales_metrics_db;
    ```

4. Grant access privileges:
    ```sql
    GRANT ALL PRIVILEGES ON DATABASE sales_metrics_db TO sales_metrics_user;
    ```

### 3. Configure Spring Boot and Liquibase

1. Add Liquibase dependency to `pom.xml`:
    ```xml
    <dependency>
        <groupId>org.liquibase</groupId>
        <artifactId>liquibase-core</artifactId>
    </dependency>
    ```

2. Configure `application.yml` for PostgreSQL and Liquibase:
    ```yaml
    spring:
      datasource:
        url: jdbc:postgresql://localhost:5432/sales_metrics_db
        username: sales_metrics_user
        password: securepassword
      jpa:
        properties:
          hibernate:
            dialect: org.hibernate.dialect.PostgreSQLDialect
        hibernate:
          ddl-auto: none
      liquibase:
        enabled: true
        change-log: classpath:db/changelog/db.changelog-master.yaml
    ```

3. Create changelog structure:
    ```
    src/main/resources/db/changelog/
    ├── db.changelog-master.yaml
    ├── changesets/
    │   ├── 0001-create-users.yaml
    │   ├── 0002-create-outreach-actions.yaml
    │   └── 0003-seed-sample-data.yaml
    ```

4. Run the application with PostgreSQL profile:
    ```bash
    mvn spring-boot:run -Dspring-boot.run.profiles=postgresql
    ```

### 5. Verify Setup

- Connect to the database using a client (e.g., pgAdmin) to verify the schema and data.
- Check application logs for Liquibase changeset execution output.
- Use Liquibase commands to verify status:
  ```bash
  mvn liquibase:status
  mvn liquibase:validate
  ```

### 6. Testing

Run the application tests to validate database interactions and Liquibase migrations:
```bash
mvn clean test
```

### 7. Liquibase Commands

Useful Liquibase Maven commands:
- `mvn liquibase:update` - Apply pending changesets
- `mvn liquibase:status` - Show changeset status
- `mvn liquibase:rollback -Dliquibase.rollbackCount=1` - Rollback last changeset
- `mvn liquibase:generateChangeLog` - Generate changelog from existing database

