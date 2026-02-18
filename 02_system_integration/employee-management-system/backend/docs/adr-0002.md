# ADR-0002: Security Architecture – Stateless JWT & RBAC

**Status:** Accepted  
**Date:** 2026-02-18

---

## Context

The backend exposes REST APIs consumed by an Angular frontend.  
The system is designed as a stateless service with strict database-enforced integrity rules.

Session-based authentication (HTTP sessions with server-side state) would:

- conflict with REST statelessness
- complicate horizontal scaling
- introduce tight coupling between backend instances

Security must be applied only after domain correctness is proven (see ADR-0001 and ADR-0003).

---

## Decision

We adopt a **Stateless, Token-Based Security Architecture** using:

- JWT (JSON Web Tokens) for authentication
- Role-Based Access Control (RBAC) for authorization
- Spring Security as the security framework

### 1. Stateless Authentication

- No HTTP sessions
- No server-side authentication state
- Each request must contain a valid JWT
- The backend validates the token signature and claims per request

### 2. Role-Based Access Control (RBAC)

- Users are assigned roles
- Roles define access permissions to endpoints and operations
- Authorization rules are enforced at the service or controller layer

### 3. Security Layering Principle

Security is introduced only after:

- domain integrity is validated
- integration tests confirm correct persistence behavior
- API contracts are stable

This ensures security protects a correct and stable system rather than masking domain flaws.

---

## Consequences

### Positive

- Fully REST-compliant architecture
- Horizontal scalability without session replication
- Clear separation between authentication and authorization
- Explicit and auditable access rules
- Clean integration with Angular frontend

### Trade-offs

- Increased implementation complexity compared to session-based auth
- Token revocation requires additional strategy (e.g., short-lived tokens or refresh tokens)
- Requires careful claim design and role modeling

These trade-offs are accepted in favor of scalability, clarity, and long-term maintainability.

---

## Conclusion

JWT-based stateless authentication combined with RBAC provides a scalable, explicit, and REST-compliant security foundation.

This decision aligns with the layered, database-first architecture defined in ADR-0001 and supports future horizontal scaling and distributed deployment scenarios.
