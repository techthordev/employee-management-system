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
* **dialog-based CRUD operations**
* production-ready **configuration & deployment practices**
* readiness for **containerized and Kubernetes-based environments**

---

## 🏗️ Architecture Overview
```
Angular SPA (Signals-first + MatDialog)
    │  HTTP (JSON, JWT planned)
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
* **Dialog-based CRUD operations** with form validation
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

The frontend is implemented using **Angular 21+ with a Signals-first architecture**.

### Signals-first Philosophy

Angular **Signals** are used as the primary state management mechanism:

* Component state is modeled using **Signals**
* Derived state uses **computed signals**
* Side effects are handled via **effects**

This approach:

* removes unnecessary RxJS complexity
* improves readability and predictability
* reflects the **current recommended Angular direction**

### Dialog-based CRUD Operations

User interactions follow Material Design patterns:

* **MatDialog** for edit and delete operations
* Single **EmployeeDialog** component with mode switching
* Form validation via **ReactiveFormsModule**
* Dialog handles API persistence
* Parent component handles refresh

This pattern:
* provides better UX with modal confirmation
* centralizes form validation
* keeps components focused and clean

### Role of RxJS

RxJS is intentionally limited to:

* HTTP communication
* truly asynchronous streams

There is **no global state library** unless strictly required.

This reflects **real-world Angular architecture in modern teams** and avoids overengineering.

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
* Angular Material
* Reactive Forms

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
│   │   ├── app/
│   │   │   ├── api/
│   │   │   │   ├── models/
│   │   │   │   └── services/
│   │   │   └── features/
│   │   │       ├── employee-dialog/
│   │   │       └── employee-list/
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

### Phase 1 — Backend Foundation ✅

* [x] Employee domain model (JPA)
* [x] Repository layer (Spring Data JPA)
* [x] Service layer with DTO mapping
* [x] REST controllers (explicit, no Spring Data REST)
* [x] Pagination & sorting via Pageable
* [x] Request validation (@Valid)
* [x] Global exception handling (@ControllerAdvice)
* [x] Consistent API error model (ApiError)
* [x] OpenAPI / Swagger documentation
* [x] Spring Boot Actuator (health, readiness, info)
* [x] Flyway database migrations (V1 initial schema)

### Phase 2 — Frontend Core CRUD ✅

* [x] Angular application setup
* [x] Signals-based state management
* [x] Employee list with pagination & sorting
* [x] Dialog-based edit operation
* [x] Dialog-based delete operation
* [x] Form validation
* [x] Auto-incrementing row numbers
* [x] Material Design UI

### Phase 3 — Create Operation

* [ ] Add employee dialog
* [ ] Form validation for create
* [ ] Error handling & feedback

### Phase 4 — Search & Filters

* [ ] Search functionality
* [ ] Column filters
* [ ] Advanced filtering

### Phase 5 — Security

* [ ] JWT authentication
* [ ] User & role model
* [ ] Method-level authorization
* [ ] Frontend JWT interceptor & guards

### Phase 6 — Deployment

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