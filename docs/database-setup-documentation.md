# Database Setup Documentation

## Overview
This document provides instructions for setting up the PostgreSQL database for the Sales Metrics API, including configuration and integration with Flyway.

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

### 3. Configure Spring Boot and Flyway

1. Add Flyway dependency to `pom.xml`:
    ```xml
    <dependency>
        <groupId>org.flywaydb</groupId>
        <artifactId>flyway-core</artifactId>
    </dependency>
    ```

2. Configure `application.properties` for PostgreSQL:
    ```properties
    # Datasource
    spring.datasource.url=jdbc:postgresql://localhost:5432/sales_metrics_db
    spring.datasource.username=sales_metrics_user
    spring.datasource.password=securepassword

    # Hibernate
    spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
    spring.jpa.hibernate.ddl-auto=none

    # Flyway
    spring.flyway.enabled=true
    spring.flyway.locations=classpath:db/migration
    spring.flyway.baseline-on-migrate=true
    ```

3. Run the application with PostgreSQL profile:
    ```bash
    mvn spring-boot:run -Dspring-boot.run.profiles=postgresql
    ```

### 4. Verify Setup

- Connect to the database using a client (e.g., pgAdmin) to verify the schema and data.
- Check application logs for Flyway migration output.

### 5. Testing

Run the application tests to validate database interactions and migrations.

