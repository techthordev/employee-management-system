# ADR-0004: Authorization Strategy – Endpoint & Method Level Enforcement

**Status:** Accepted  
**Date:** 2026-02-18

---

## Context

The backend exposes multiple REST resources with different sensitivity levels:

- Reference data (Departments, Projects)
- Business data (Employees, Profiles, Assignments)

Authorization must:

- be explicit
- be reviewable
- not rely on URL conventions alone
- not leak rules into the frontend

Relying only on controller-level security is insufficient for long-term maintainability, as business rules may evolve independently of transport-layer structure.

---

## Decision

We adopt a **dual-layer authorization strategy** combining:

- Endpoint-level access control
- Method-level authorization enforcement

### 1. Endpoint-Level Enforcement

Basic access rules are defined at the HTTP layer:

- Authentication required for protected routes
- Role-based restrictions where appropriate
- Clear mapping between role and endpoint access

This provides a first line of defense and ensures predictable API exposure.

---

### 2. Method-Level Enforcement

Critical business rules are enforced at the service layer using method-level security annotations.

Examples:

- Role-based checks (`hasRole(...)`)
- Authority-based checks (`hasAuthority(...)`)
- Contextual validation where required

This ensures:

- protection independent of controller structure
- resilience against refactoring
- enforcement even if the transport layer changes

Business rules remain protected regardless of transport mechanism or architectural evolution.

---

### 3. Principle of Explicit Authorization

Authorization rules must:

- be declared close to the protected logic
- remain visible in code reviews
- be testable

Security decisions are not inferred implicitly from naming conventions or routing structure.

---

## Consequences

### Positive

- Clear separation between transport security and business rule enforcement
- Reduced risk of accidental exposure during refactoring
- Explicit, reviewable security boundaries
- Future-proof against API restructuring or additional entry points

### Trade-offs

- Slightly increased annotation overhead
- Requires discipline to maintain consistency
- Developers must understand layered security interactions

These trade-offs are accepted in favor of clarity, explicitness, and long-term maintainability.

---

## Architectural Alignment

This strategy:

- Builds upon the stateless JWT authentication model (ADR-0002)
- Protects domain integrity validated through integration testing (ADR-0003)
- Reinforces the layered architecture defined in ADR-0001

Authorization is enforced where business logic resides, not merely where HTTP requests enter the system.

---

## Conclusion

Authorization is enforced at both endpoint and method level to ensure:

- explicit security boundaries
- protection against refactoring side effects
- long-term maintainability

Security rules remain stable and enforceable regardless of transport or structural changes.
