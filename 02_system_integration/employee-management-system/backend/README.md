# Employee Management System – Backend

## Overview

This project is a **Spring Boot backend** for an Employee Management System.
It exposes REST APIs consumed by an Angular frontend and uses **PostgreSQL**
as its relational database.

The backend follows a clean layered architecture and a strict
foreign-key–driven entity dependency strategy to ensure data integrity
and long-term maintainability.

---

## Technology Stack

- Java 25+
- Spring Boot 4+
- Spring Data JPA (Hibernate)
- PostgreSQL
- Gradle
- Virtual Threads (Project Loom)
- REST APIs

---

## Project Structure

```

br.com.techthordev.backend
├── entity
├── repository
├── service
├── controller
└── config

````

### Responsibilities

- **entity** – JPA mappings aligned with the database schema
- **repository** – Spring Data JPA interfaces
- **service** – Business logic and transaction handling
- **controller** – REST endpoints
- **config** – Security and infrastructure configuration

---

## Database Schema (public)

The application uses the **PostgreSQL `public` schema**.

![Public Scheme](img/employee_management-public-scheme.png)

The diagram is the **authoritative source of truth** for:

- table definitions
- foreign key relationships
- dependency hierarchy
- creation order

All entities must strictly match this schema.

Hibernate is configured with:

```yaml
spring.jpa.hibernate.ddl-auto: validate
````

This guarantees:

* Schema mismatches prevent application startup
* Entities cannot diverge from the database structure
* Schema changes must be handled explicitly

The database is the ultimate source of truth.

---

## Entity Dependency Strategy

The backend follows a strict foreign-key–based entity hierarchy.

Entities are grouped into dependency levels based on database
foreign key relationships.
The level system defines:

* creation order
* test order
* deletion safety
* architectural boundaries

---

## Dependency Levels

### Level 0 – Independent Tables

Tables without foreign key dependencies.

Characteristics:

* No `@ManyToOne` or `@OneToOne` dependencies
* Can be created, tested, and deleted independently
* Form the structural foundation of the domain model

Examples:

* `Department`
* `Project`

---

### Level 1 – Simply Dependent Tables

Tables that depend on exactly one Level 0 table.

Characteristics:

* Single foreign key dependency
* Cannot exist without the referenced Level 0 entity
* Represent primary domain ownership

Example:

* `Employee` → depends on `Department`

---

### Level 2 – Strongly Dependent Tables

Tables that depend on Level 1 entities or represent composed relationships.

Characteristics:

* One-to-One or Many-to-Many relationships
* Often implemented as extension or join tables
* Exist only in context of owning entities

Examples:

* `EmployeeProfile` → One-to-One with `Employee`
* `EmployeeProject` → Join table (`Employee` ↔ `Project`)

---

## Implementation Status

### Level 0 – Completed

Implemented:

* `Department`
* `Project`

Includes:

* JPA entities aligned with schema
* Repositories
* Integration tests against a real PostgreSQL instance

Validated aspects:

* Schema alignment (`ddl-auto: validate`)
* ID generation
* Optimistic locking (`@Version`)
* CRUD behavior

---

### Level 1 – Completed

Implemented:

* `Employee`

Dependency:

* Foreign key: `department_id`
* `@ManyToOne` → `Department`

Database constraint:

```sql
FOREIGN KEY (department_id)
REFERENCES public.department(id)
ON DELETE RESTRICT
```

Integrity guarantees:

* Employees must belong to a department
* Departments with employees cannot be deleted
* Violations result in `DataIntegrityViolationException`

---

### Level 2 – Planned

To be implemented:

* `EmployeeProfile` (One-to-One with `Employee`)
* `EmployeeProject` (Many-to-Many join table)

These introduce stronger relational coupling and require
explicit ownership and cascade decisions.

---

## Repository Strategy

Repositories follow the entity dependency hierarchy.

Implemented:

* `DepartmentRepository`
* `ProjectRepository`
* `EmployeeRepository`

Repository tests validate:

* CRUD behavior
* Foreign key enforcement
* Constraint violation handling

---

## Data Integrity Strategy

Integrity is enforced at multiple layers.

### 1. Database Layer (Primary Enforcement)

* NOT NULL constraints
* FOREIGN KEY constraints
* UNIQUE constraints
* CHECK constraints

### 2. JPA Layer (Schema Alignment)

* `nullable = false`
* `optional = false`
* `@Version` for optimistic locking
* Explicit fetch strategies

### 3. Application Layer (Optional Enhancements)

* Bean Validation
* Service-level validation

Database constraints are mandatory.
Application-level validation improves UX but never replaces database rules.

---

## Development Rules

* Follow the dependency hierarchy strictly
* Keep schema diagram and database in sync
* Validate repositories after schema changes
* Database constraints must match JPA annotations
* Constraint violations must be tested explicitly

---

## Next Steps

* Implement Level 2 entities
* Define ownership and cascade strategies
* Extend repository integration tests accordingly

