# Employee Management System – Backend

## Overview

This project is a **Spring Boot backend** for an Employee Management System.
It exposes REST APIs consumed by an Angular frontend and uses **PostgreSQL**
as its relational database.

The backend follows a clean layered architecture and a strict
entity-dependency strategy to ensure data integrity and maintainability.

---

## Technology Stack

- Java 25+
- Spring Boot 4+
- Spring Data JPA (Hibernate)
- PostgreSQL
- Gradle
- Virtual Threads (Project Loom)
- RESTful APIs

---

## Project Structure

```
br.com.techthordev.backend
 ├── entity        (JPA Mappings for public/auth schemas)
 ├── repository    (Spring Data JPA Interfaces)
 ├── controller    (REST Endpoints for Angular)
 ├── service       (Business Logic / Virtual Thread handling)
 └── config        (Security & Virtual Thread configurations)
```


### Responsibilities

- **entity**: JPA entities mapped to database tables
- **repository**: Persistence layer (JpaRepository)
- **service**: Business logic and transactions
- **controller**: REST API endpoints
- **config**: Security and infrastructure configuration

---

## Public Schema

The application uses the **PostgreSQL `public` schema**.

The following diagram shows the **complete database schema**,
including all tables and foreign key relationships:

![Public Scheme](img/employee_management-public-scheme.png)

This diagram is the **authoritative source of truth** for:
- table definitions
- foreign keys
- entity relationships
- dependency hierarchy
- correct JPA mappings

All entities must strictly match this schema.

---

## Entity Dependency Strategy

Entities are implemented in **dependency levels** to avoid foreign key
violations and to allow incremental development and testing.

---

### Level 0 – Independent Entities (Completed)

These entities do **not depend on any other tables**:

- `Department`
- `Project`

Status:
- JPA entities implemented
- Repositories implemented
- Mapped to the `public` schema
- Can be tested independently

---

### Level 0 – Repository Integration Tests

Before introducing dependent entities, Level 0 entities (`Department`, `Project`)
are validated using **repository integration tests** against a real PostgreSQL
instance running in a container.

### Test Strategy

Level 0 tests verify:

- Database connectivity (PostgreSQL in container)
- Correct schema usage (`public`)
- JPA entity mappings
- ID generation strategy
- Optimistic locking (`@Version`)
- Repository CRUD operations

Tests are executed using:

- `@SpringBootTest`
- Spring Data JPA repositories
- A dedicated `test` profile
- Real database (no in-memory database)

### Transaction Handling

All repository tests run inside a transaction:

```java
@Transactional
````

Each test is automatically rolled back after execution.
No data is persisted permanently in the database.

### Schema Validation

Hibernate is configured with:

```yaml
spring.jpa.hibernate.ddl-auto: validate
```

This ensures:

* The database schema must exactly match the JPA entities
* Any mismatch (missing columns, wrong nullability, wrong types) causes startup failure
* Schema changes must be handled explicitly (e.g. via Flyway)

This setup enforces strict schema discipline and prevents accidental
runtime schema changes.

### Running the Tests

Tests can be executed with:

```bash
SPRING_PROFILES_ACTIVE=test ./gradlew clean test
```

A successful run confirms that Level 0 is stable and ready
for dependent entities (Level 1).

---

### Level 1 – Dependent Entity (Current)

#### `Employee`

Dependencies:
- Requires an existing `Department`
- Foreign key: `department_id`

Rules:
- A Department must exist before creating an Employee
- JPA mapping uses `@ManyToOne`
- Must match the schema diagram exactly

Components:
- `Employee` entity
- `EmployeeRepository`

---

### Level 2 – Strongly Dependent Entities (Next)

- `EmployeeProfile`
    - One-to-One relationship with `Employee`
- `EmployeeProject`
    - Join table between `Employee` and `Project`
    - Represents a Many-to-Many relationship

These introduce more complex relational mappings.

---

## Repository Strategy

Repositories are created according to the dependency hierarchy.

### Level 0 Repositories (Completed)
- `DepartmentRepository`
- `ProjectRepository`

### Level 1 Repository (Next)
- `EmployeeRepository`

At this stage:
- No custom queries
- Focus on correctness and foreign key integrity

---

## Development Rules

- Always follow the entity dependency hierarchy
- The schema diagram must stay in sync with the database
- Any schema change requires:
    - updating the diagram
    - updating entities
    - validating repositories

---

## Next Steps

1. Implement `Employee` entity
2. Implement `EmployeeRepository`
3. Validate foreign key behavior
4. Continue with Level 2 entities


