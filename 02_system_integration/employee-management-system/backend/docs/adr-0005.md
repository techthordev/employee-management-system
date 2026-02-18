# 📄 ADR-0005: JWT Lifecycle & Token Strategy

**Status:** Accepted

**Date:** 2026-02-18

---

## Context

The backend is a stateless REST API secured via **JWT** using **Spring Security** (ADR-0002, ADR-0004).
The frontend (Angular) authenticates via HTTP and must not rely on server-side session state.

JWT usage introduces lifecycle questions that must be decided **explicitly**, not implicitly by framework defaults:

* Token lifetime
* Revocation strategy
* Refresh behavior
* Storage responsibility
* Trust boundaries

Unclear JWT lifecycle decisions can lead to:

* security gaps
* token leakage risks
* unbounded session duration
* unmanageable logout semantics

---

## Decision

We adopt a **Simple, Explicit JWT Lifecycle Strategy** optimized for **clarity, statelessness, and security predictability**.

---

## JWT Strategy Overview

### 1️⃣ Access Token Only (No Refresh Tokens – Initial Phase)

* One JWT type: **Access Token**
* Short to medium lifetime
* Stateless validation on every request

**Rationale**

* Keeps security model simple
* Avoids server-side token state
* Sufficient for internal / enterprise systems
* Refresh tokens can be added later without breaking API contracts

---

### 2️⃣ Token Lifetime Policy

* Tokens are **time-bound**
* Expiration is enforced strictly
* Expired tokens are rejected unconditionally

**Rationale**

* Limits impact of token leakage
* Predictable security behavior
* No “silent re-authentication”

---

### 3️⃣ No Server-Side Token Storage

* Tokens are **not persisted** in the database
* No token blacklist
* No session table

**Rationale**

* Preserves stateless architecture
* Avoids synchronization issues
* Simplifies horizontal scaling

**Implication**

* Token revocation happens implicitly via expiration
* Immediate logout is client-side only

This trade-off is accepted (see Consequences).

---

### 4️⃣ Logout Semantics

* Logout is handled on the **client**
* Token is deleted from client storage
* Backend remains stateless

**Rationale**

* Server cannot invalidate what it does not store
* Aligns with REST principles
* Predictable and transparent behavior

---

### 5️⃣ JWT Payload Scope

JWTs contain **minimal identity and authority data only**:

* User identifier
* Granted roles
* Token metadata (issued at, expiration)

**Explicitly excluded**

* Business data
* Permissions derived from DB joins
* Mutable domain state

**Rationale**

* Prevents stale authorization data
* Keeps tokens small
* Avoids business logic in security layer

---

## Consequences

### Positive

* Fully stateless authentication
* Simple mental model
* Easy horizontal scaling
* No token synchronization required
* Security behavior is deterministic

### Trade-offs

* No immediate server-side revocation
* Logout relies on expiration
* Requires careful expiration tuning

These trade-offs are accepted in favor of simplicity, clarity, and architectural consistency.

---

## Alignment with Other ADRs

* **ADR-0001:** Database remains authority for business rules
* **ADR-0002:** Stateless JWT-based authentication confirmed
* **ADR-0003:** Security relies on tested domain integrity
* **ADR-0004:** Authorization enforced at endpoint & method level

JWTs provide **identity**, not **business truth**.

---

## Future Extensions (Explicitly Deferred)

The following are **intentionally out of scope** for the initial implementation:

* Refresh tokens
* Token blacklisting
* OAuth2 / OpenID Connect
* Distributed revocation mechanisms

They can be introduced later via a new ADR **without breaking existing clients**.

---

## Conclusion

JWTs are treated as **short-lived proof of identity**, not as sessions.
The system favors **predictability and statelessness** over immediate revocation.

Security behavior is explicit, reviewable, and aligned with REST principles.
