# ADR-0001: Backend Architecture for Employee Management System

**Status:** Accepted  
**Date:** 2026-02-18

---

## Context

The system provides a REST-based backend consumed by an Angular frontend.  
The domain is data-centric (Employees, Departments, Projects) and requires:

- strict referential integrity
- clear and stable API contracts
- long-term maintainability

Past experience shows that ORM-driven schema generation and entity-exposed APIs often lead to:

- data corruption
- unstable tests
- security risks
- tight coupling between layers

---

## Decision

We adopt a **Layered, Database-First Architecture** based on the following principles.

### 1. Strict Layer Separation

- Controller → Service → Repository → Database
- No upward dependencies between layers

### 2. Database as Source of Truth

- PostgreSQL schema is authoritative
- Hibernate schema generation is disabled
- Configuration:
```yaml
  hibernate.ddl-auto: validate
```

* Integrity is enforced at the database level using:

    * FOREIGN KEY
    * NOT NULL
    * UNIQUE constraints

### 3. Foreign-Key–Driven Entity Dependency Levels

Entities are grouped by dependency level:

* **Level 0:** Independent entities
* **Level 1:** Simple foreign key dependencies
* **Level 2:** 1:1 and N:M relations using composite keys

This ensures deterministic creation order, testability, and data integrity.

### 4. DTO-Based API Design

* Separate DTOs for:

    * Create requests
    * Update requests
    * API responses
* JPA entities are never exposed via REST
* Entity ↔ DTO mapping is handled via MapStruct

    * compile-time generation
    * no runtime reflection

### 5. Integration Testing over Mocks

* Tests run against a real PostgreSQL instance
* Constraint violations are explicitly validated
* Tests act as **living architectural specifications**

### 6. Security after Domain Stabilization

* Domain integrity is validated first
* Security is layered on top of a proven model
* Spring Security with JWT and RBAC is introduced only after domain correctness is ensured

---

## Consequences

### Positive

* Strong data integrity guarantees
* Clear and stable API contracts
* Predictable development and testing order
* Architectural decisions are continuously validated by tests
* Security integrates cleanly on top of a stable foundation

### Trade-offs

* Slightly higher upfront effort
* Slower than pure unit-test-based approaches
* Requires discipline in schema evolution

These trade-offs are accepted in favor of correctness, clarity, and long-term maintainability.

---

## Conclusion

This architecture prioritizes:

* correctness over convenience
* explicit decisions over framework magic
* database integrity over ORM flexibility

It is well-suited for enterprise-grade REST APIs consumed by Angular frontends and provides a stable foundation for future security, scalability, and maintainability.
