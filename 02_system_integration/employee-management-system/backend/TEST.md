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

## 3. Test Isolation Strategy

### The Challenge
Standard `@Transactional` rollback mechanisms don't guarantee clean state when:
- Using `saveAndFlush()` to test constraint violations
- Running multiple tests in sequence
- Database has UNIQUE constraints

### Our Solution: Explicit Cleanup
We use a `@BeforeEach` hook in `BaseDomainTest` that:

1. **Deletes all entities** in FK-safe order (Level 2 → 1 → 0)
2. **Flushes changes** to force immediate DELETE execution
3. **Guarantees isolation** between test methods
```java
@BeforeEach
void setUp() {
    employeeProjectRepository.deleteAll();  // Level 2
    employeeRepository.deleteAll();          // Level 1
    projectRepository.deleteAll();           // Level 0
    departmentRepository.deleteAll();        // Level 0
    employeeProjectRepository.flush();       // Commit deletes
}
```

### Benefits
- ✅ No UUID suffixes needed in test data
- ✅ Exact assertions possible (e.g., `isEqualTo("Cloud Migration")`)
- ✅ Tests remain readable and maintainable
- ✅ True isolation between test executions

### Trade-offs
- Slightly slower than pure rollback (negligible for our scale)
- Requires careful ordering to respect FK constraints
- Must remember to inject all repositories in `BaseDomainTest`

## 4. Tooling Setup
* **Database:** PostgreSQL 18.2 (Podman).
* **Context:** `SPRING_PROFILES_ACTIVE=test` ensures `ddl-auto: validate`.
* **IDE:** IntelliJ IDEA with "Show Passed" enabled to view the `@DisplayName` hierarchy.
```
