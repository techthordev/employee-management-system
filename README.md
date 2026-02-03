# Employee Management System

> **Production-grade full-stack employee management system**
> built with Spring Boot, Angular (Signals-first), PostgreSQL, and modern enterprise best practices.

[![Java](https://img.shields.io/badge/Java-25+-orange?logo=openjdk)](https://openjdk.org/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.0-brightgreen?logo=spring)](https://spring.io/projects/spring-boot)
[![Angular](https://img.shields.io/badge/Angular-21+-red?logo=angular)](https://angular.io/)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-18+-blue?logo=postgresql)](https://www.postgresql.org/)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

---

## 🎯 Purpose

This project is a **portfolio-grade, production-oriented employee management system**.

It is intentionally designed to reflect **how Spring Boot backends and modern Angular frontends are built in real-world, enterprise environments** — without framework magic, auto-exposed APIs, or tutorial shortcuts.

The goal is to demonstrate:

* clean **REST API design**
* explicit **layered backend architecture**
* modern **Angular state management using Signals**
* production-ready **configuration & deployment practices**
* readiness for **containerized and Kubernetes-based environments**

---

## 🏗️ Architecture Overview

```
Angular SPA (Signals-first)
    │  HTTP (JSON, JWT)
    ▼
Spring Boot REST API
    │  Service Layer
    ▼
Spring Data JPA
    │
PostgreSQL
```

### Architectural Principles

* **Explicit REST controllers** (no Spring Data REST)
* **Clear separation of concerns** (Controller → Service → Repository)
* **DTO-based API contracts** decoupled from persistence models
* **Frontend state handled via Angular Signals**
* **Configuration via environment variables** (12-factor compliant)
* **Database schema managed via migrations**

This architecture mirrors what is commonly used in **professional backend and frontend teams**.

---

## 🧠 Backend Architecture (Layered)

```
Controller  →  Service  →  Repository  →  Database
(API)          (Business)    (JPA)
```

### Responsibilities

* **Controller Layer**

  * REST endpoints
  * HTTP semantics & status codes
  * Request/response validation
  * OpenAPI documentation

* **Service Layer**

  * Business logic
  * Transaction boundaries
  * Cross-entity orchestration

* **Repository Layer**

  * Data access only
  * JPA & query definitions

* **Domain Layer**

  * JPA entities
  * Persistence mapping

* **DTOs & Mappers**

  * API contracts isolated from entities
  * Controlled data exposure

---

## 🧠 Frontend Architecture (Angular 21+)

The frontend is implemented using **Angular 21+ with a Signals-first approach**.

### State Management Strategy

* **Angular Signals** for local and shared UI state
* **RxJS** used only for:

  * HTTP requests
  * async streams
* No global state libraries unless strictly necessary

This reflects **current Angular best practices** and avoids unnecessary complexity.

---

## 🛠️ Tech Stack

### Backend

* Java 25+
* Spring Boot 4.0.x
* Spring Web MVC (explicit REST controllers)
* Spring Data JPA
* PostgreSQL 18+
* Flyway (database migrations)
* Spring Boot Actuator (health & readiness)
* OpenAPI / Swagger
* JWT-based authentication (planned)
* Maven

### Frontend

* Angular 21+
* TypeScript
* Angular Signals
* RxJS (HTTP & streams)
* Angular Material (planned)

### DevOps / Infrastructure

* Podman / Docker (local development)
* Environment-based configuration
* Kubernetes-ready design
* CI/CD pipeline (planned)

---

## 📂 Project Structure

```
employee-management-system/
├── backend/              # Spring Boot REST API
│   ├── src/
│   ├── pom.xml
│   └── README.md
├── frontend/             # Angular SPA (Signals-first)
│   ├── src/
│   ├── package.json
│   └── README.md
├── database/             # SQL reference & migration context
├── compose.yml           # Local container setup
└── README.md
```

---

## 🗄️ Database Management

Database schema evolution is handled using **Flyway migrations**.

### Why Flyway?

* reproducible schema state
* safe upgrades across environments
* required for containerized and Kubernetes deployments

Manual schema management is intentionally avoided in favor of **versioned migrations**.

---

## 🔐 Security (Planned)

* Stateless JWT authentication
* Role-based access control
* Secure API boundaries
* Swagger UI secured via JWT

The security model follows **industry-standard Spring Security practices**.

---

## ❤️ Health & Readiness

The application exposes health information via **Spring Boot Actuator**.

* `/actuator/health` — liveness & readiness

This enables:

* container orchestration
* Kubernetes probes
* safe rolling deployments

---

## 🚀 Development & Deployment Philosophy

* **Local development** with Podman/Docker
* **Configuration via environment variables**
* No secrets in source control
* Same application artifact across all environments

The application is designed to run unchanged in:

* local development
* container environments
* Kubernetes clusters

---

## 📋 Roadmap

### Phase 1 — Backend Foundation

* [x] Employee domain model
* [x] Repository layer
* [x] Service layer
* [x] REST controllers
* [x] DTO-based API contracts
* [x] Request validation (@Valid)
* [x] Global exception handling (`@ControllerAdvice`)
* [x] OpenAPI / Swagger configuration
* [x] Flyway database migrations (V1 initial schema)

### Phase 2 — Security

* [ ] JWT authentication
* [ ] User & role model
* [ ] Method-level authorization

### Phase 3 — Frontend

* [ ] Angular application setup
* [ ] Signals-based state management
* [ ] Employee CRUD views
* [ ] JWT interceptor & guards

### Phase 4 — Deployment

* [ ] Container image
* [ ] CI/CD pipeline
* [ ] Kubernetes manifests

---

## 👨‍💻 Author

**Thorsten Fey**
IT Support → Backend Developer

* 🌍 [https://techthordev.com.br](https://techthordev.com.br)
* 💼 [https://linkedin.com/in/thorstenfey](https://linkedin.com/in/thorstenfey)
* 💻 [https://github.com/techthordev](https://github.com/techthordev)

---

## 📝 License

MIT License — see [LICENSE](LICENSE)

---

⭐ This repository is intentionally designed as a **clean, realistic reference project** demonstrating how modern Spring Boot backends and Angular (Signals-first) frontends are built in practice.
