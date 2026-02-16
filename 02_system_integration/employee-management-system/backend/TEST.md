# Testing Specification - Entity Dependency Strategy

## 1. Rationale
We employ a **Foreign-Key-Driven Integration Test Strategy**. Since our PostgreSQL database is the authoritative source of truth, unit tests with mocks are insufficient. We must validate that the application respects the physical constraints of the schema.

## 2. Test Hierarchy (The Level System)

### Level 0: Foundation (Independent)
* **Target:** Entities with no outgoing Foreign Keys (`Department`, `Project`).
* **Purpose:** Validate table existence, auditing columns (`created_at`), and optimistic locking (`@Version`).
* **Failure Mode:** If Level 0 fails, the entire system is unstable.

### Level 1: Simple Dependencies
* **Target:** Entities depending on exactly one Level 0 entity (`Employee`).
* **Purpose:** Validate `NOT NULL` constraints and `ON DELETE RESTRICT` behavior.
* **Assertion Logic:** We explicitly trigger `DataIntegrityViolationException` to prove that Java cannot bypass DB rules.

### Level 2: Complex & Composed Relations
* **Target:** `EmployeeProfile` (1:1), `EmployeeProject` (N:M).
* **Purpose:** Validate Composite Keys (`@EmbeddedId`) and Shared Primary Keys.
* **Assertion Logic:** Ensures referential integrity across multiple tables and prevents orphaned records.

## 3. Tooling Setup
* **Database:** PostgreSQL 18.2 (Podman).
* **Context:** `SPRING_PROFILES_ACTIVE=test` ensures `ddl-auto: validate`.
* **IDE:** IntelliJ IDEA with "Show Passed" enabled to view the `@DisplayName` hierarchy.