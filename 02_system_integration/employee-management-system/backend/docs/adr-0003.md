# ADR-0003: Testing Strategy – Foreign-Key-Driven Integration Validation

**Status:** Accepted  
**Date:** 2026-02-18

---

## Context

The Employee Management System backend is built with:

- Spring Boot  
- Spring Data JPA  
- PostgreSQL  

The database schema is the **authoritative source of truth**, enforced via:

```yaml
spring.jpa.hibernate.ddl-auto: validate
```

Business integrity rules (FOREIGN KEY, NOT NULL, UNIQUE constraints, composite keys) are defined **physically in PostgreSQL**.

Traditional unit tests with mocks cannot validate:

* foreign key enforcement
* `ON DELETE RESTRICT` behavior
* composite primary keys
* optimistic locking (`@Version`)
* schema alignment between Java and database

Therefore, a higher-level validation strategy is required.

---

## Decision

We adopt a **Foreign-Key-Driven Integration Test Strategy** based on the following principles.

---

### 1. Real Database over Mocks

All repository tests run against a real PostgreSQL instance (test profile).

We explicitly verify that:

* constraint violations throw `DataIntegrityViolationException`
* foreign key relationships are enforced
* `ON DELETE RESTRICT` prevents invalid state
* composite keys behave correctly

Mock-based repository tests are intentionally avoided.

---

### 2. Level-Based Test Hierarchy (0–2 Model)

Tests mirror the entity dependency architecture.

|       Level | Scope                  | Purpose                                           |
| ----------: | ---------------------- | ------------------------------------------------- |
| **Level 0** | Independent tables     | Validate base schema and auditing                 |
| **Level 1** | Simple FK dependencies | Validate NOT NULL and FK behavior                 |
| **Level 2** | 1:1 and N:M relations  | Validate composite keys and referential integrity |

This guarantees:

* deterministic test setup
* logical development order
* clear failure diagnostics

Failure interpretation:

* Level 0 failure → system instability
* Level 1 failure → relational integrity risk
* Level 2 failure → business logic corruption

---

### 3. Explicit Constraint Validation

Tests intentionally provoke constraint violations:

```java
assertThatThrownBy(() -> employeeRepository.saveAndFlush(employee))
    .isInstanceOf(DataIntegrityViolationException.class);
```

Purpose:

* prove that Java cannot bypass PostgreSQL
* ensure database constraints remain active
* detect accidental schema relaxation

Tests act as **architecture guards**, not just functional checks.

---

### 4. Explicit Test Isolation Strategy

Standard `@Transactional` rollback is insufficient when:

* using `saveAndFlush()`
* testing UNIQUE constraints
* running multiple constraint scenarios

Therefore, explicit cleanup is applied:

```java
@BeforeEach
void setUp() {
    employeeProjectRepository.deleteAll();
    employeeRepository.deleteAll();
    projectRepository.deleteAll();
    departmentRepository.deleteAll();
    employeeProjectRepository.flush();
}
```

Deletion order respects the FK hierarchy (Level 2 → Level 1 → Level 0).

**Trade-off:** slightly slower than rollback
**Benefit:** deterministic and clean database state

---

### 5. Tests as Living Specifications

`@DisplayName` is used to express business rules explicitly.

Example:

```
CONSTRAINT CHECK: Must reject null FK
```

Tests document:

* what the database guarantees
* what the architecture forbids
* what the system must never allow

They serve as executable documentation of ADR-0001.

---

## Consequences

### Positive

* strong validation of physical data integrity
* early detection of schema drift
* prevention of orphaned records
* alignment between Java model and PostgreSQL
* high confidence before introducing the security layer

### Trade-offs

* slower than pure unit tests
* requires real database setup
* requires disciplined cleanup ordering

These trade-offs are accepted in favor of correctness and architectural enforcement.

---

## Architectural Alignment

This testing strategy ensures:

* the database remains the ultimate authority
* the layered architecture cannot bypass constraints
* future integration of security builds upon verified integrity
* the Angular frontend consumes a backend proven to respect relational guarantees

---

## Conclusion

Testing is not limited to behavior validation.

In this architecture, testing is:

* a schema verification mechanism
* an ADR enforcement layer
* a data integrity guarantee

The system prioritizes correctness, determinism, and explicit architectural validation over speed or convenience.
