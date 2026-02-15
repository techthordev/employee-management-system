# Employee Management System – Backend

## Overview

This project is a **Spring Boot backend** for an Employee Management System.
It exposes REST APIs consumed by an Angular frontend and uses **PostgreSQL**
as its relational database.

The backend follows a clean layered architecture and a strict
entity-dependency strategy to ensure data integrity and maintainability.

---

## Technology Stack

* Java 25+
* Spring Boot 4+
* Spring Data JPA (Hibernate)
* PostgreSQL
* Gradle
* Virtual Threads (Project Loom)
* REST APIs

---

## Project Structure

```
br.com.techthordev.backend
 ├── entity
 ├── repository
 ├── service
 ├── controller
 └── config
```

### Responsibilities

* **entity** – JPA mappings aligned with the database schema
* **repository** – Spring Data JPA interfaces
* **service** – Business logic and transaction handling
* **controller** – REST endpoints
* **config** – Security and infrastructure configuration

---

## Database Schema (public)

The application uses the **PostgreSQL `public` schema**.

![Public Scheme](img/employee_management-public-scheme.png)

The diagram is the authoritative reference for:

* table definitions
* foreign keys
* entity relationships
* dependency hierarchy

All entities must strictly match this schema.

Hibernate is configured with:

```yaml
spring.jpa.hibernate.ddl-auto: validate
```

This guarantees:

* The schema must match the entities exactly
* Any mismatch causes application startup failure
* Schema changes must be handled explicitly

The database is the ultimate source of truth.

---

# Entity Dependency Strategy

Entities are implemented according to foreign key dependency levels.
This prevents constraint violations and enables incremental development.

---

## Entity Dependency Levels

Entities are grouped into dependency levels based on their
**foreign key relationships**.

The level system defines **creation order, test order, and deletion safety**.

---

### Level 0 – Independent Tables

Tables without foreign key dependencies.

Characteristics:
- No `@ManyToOne` or `@OneToOne` dependencies
- Can be created, tested, and deleted independently
- Form the foundation of the domain model

Examples:
- `Department`
- `Project`

---

### Level 1 – Simply Dependent Tables

Tables that depend on exactly one Level 0 table.

Characteristics:
- Single foreign key dependency
- Cannot exist without the referenced Level 0 entity
- Represent core domain relationships

Example:
- `Employee` → depends on `Department`

---

### Level 2 – Strongly Dependent Tables

Tables that depend on Level 1 entities or represent
composed relationships.

Characteristics:
- One-to-One or Many-to-Many relationships
- Often implemented as extension or join tables
- Cannot exist independently

Examples:
- `EmployeeProfile` → One-to-One with `Employee`
- `EmployeeProject` → Join table (`Employee` ↔ `Project`)

---

## Level 0 – Independent Entities (Completed)

Entities without foreign key dependencies:

* `Department`
* `Project`

Status:

* Entities implemented
* Repositories implemented
* Repository integration tests implemented

These can be tested independently.

---

### Level 0 – Repository Integration Tests

Level 0 entities are validated against a real PostgreSQL container.

Test characteristics:

* `@SpringBootTest`
* Real database (no in-memory DB)
* `test` profile
* `@Transactional` with automatic rollback

Validated aspects:

* Database connectivity
* Schema usage (`public`)
* JPA mappings
* ID generation
* Optimistic locking (`@Version`)
* CRUD behavior

Run tests:

```bash
SPRING_PROFILES_ACTIVE=test ./gradlew clean test
```

A successful run confirms Level 0 stability.

---

## Level 1 – Dependent Entity (Completed)

### `Employee`

Dependency:

* Requires existing `Department`
* Foreign key: `department_id`

Rules:

* A department must exist before creating an employee
* `@ManyToOne` mapping
* Must match schema exactly

Components implemented:

* `Employee` entity
* `EmployeeRepository`
* Integration tests

### Database Constraints

```sql
CREATE TABLE public.employee (
    ...
    department_id BIGINT NOT NULL,
    ...
    CONSTRAINT fk_employee_department 
        FOREIGN KEY (department_id) 
        REFERENCES public.department(id) 
        ON DELETE RESTRICT
);
```

Integrity rules:

* NOT NULL: Employee must belong to a department
* ON DELETE RESTRICT: Prevents deleting departments with employees
* Constraint violations trigger `DataIntegrityViolationException`

Status: Completed and validated.

---

## Level 2 – Strongly Dependent Entities (Next)

Planned:

* `EmployeeProfile`

  * One-to-One with `Employee`
* `EmployeeProject`

  * Join table (`Employee` ↔ `Project`)
  * Many-to-Many relationship

These introduce more complex relational mappings.

---

## Repository Strategy

Repositories follow the entity dependency hierarchy.

Implemented:

* `DepartmentRepository`
* `ProjectRepository`
* `EmployeeRepository`

Repository tests validate:

* CRUD operations
* Foreign key enforcement
* Constraint violation handling

---

## Data Integrity Strategy

The system enforces integrity at multiple layers.

### 1. Database Layer (Primary Enforcement)

* NOT NULL constraints
* FOREIGN KEY constraints
* UNIQUE constraints
* CHECK constraints

### 2. JPA Layer (Alignment & Documentation)

* `nullable = false`
* `optional = false`
* `@Version` for optimistic locking
* Proper fetch strategies

### 3. Application Layer (Optional Enhancements)

* Bean Validation
* Service-level validation

Philosophy:
Database constraints are mandatory.
Application validations improve user experience but do not replace database rules.

---

## Development Rules

* Follow the dependency hierarchy
* Keep schema diagram and database in sync
* Validate repositories after schema changes
* Database constraints must match JPA annotations
* Constraint violations must be tested

---

## Next Steps

* Implement `EmployeeProfile` (One-to-One)
* Implement `EmployeeProject` (Many-to-Many join table)
