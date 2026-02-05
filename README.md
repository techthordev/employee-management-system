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
    │  API: /api/v1/*
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
* **Versioned API endpoints** (`/api/v1/*` for backward compatibility)
* **Clear separation of concerns** (Controller → Service → Repository)
* **DTO-based API contracts** decoupled from persistence models
* **Frontend state handled via Angular Signals**
* **Configuration via environment variables** (12-factor compliant)
* **Database schema managed via migrations**

This architecture mirrors what is commonly used in **professional backend and frontend teams**.

### API Versioning Strategy

The backend follows a **URI-based versioning strategy**:

* All endpoints are prefixed with `/api/v1/`
* Enables non-breaking evolution of the API
* Follows REST best practices for API lifecycle management
* Frontend configures base URL via dependency injection for flexibility

Example endpoints:
* `GET /api/v1/employees` - List all employees (paginated)
* `POST /api/v1/employees` - Create new employee
* `GET /api/v1/employees/{id}` - Get employee by ID
* `PUT /api/v1/employees/{id}` - Update employee
* `DELETE /api/v1/employees/{id}` - Delete employee

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

### Role of RxJS

RxJS is intentionally limited to:

* HTTP communication
* truly asynchronous streams

There is **no global state library** unless strictly required.

This reflects **real-world Angular architecture in modern teams** and avoids overengineering.

### Dependency Injection Patterns

The frontend follows Angular DI best practices:

* **InjectionToken** for API base URL configuration
* Environment-agnostic service layer
* Centralized provider configuration in `app.config.ts`
* Type-safe dependency injection throughout

This enables:

* seamless deployment across environments (dev, staging, prod)
* testability through provider mocking
* clean separation of configuration from business logic

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
* Standalone components architecture

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
│   │   │   │   ├── services/      # HTTP services
│   │   │   │   ├── models/        # TypeScript interfaces
│   │   │   │   └── tokens/        # Injection tokens
│   │   │   ├── features/          # Feature modules
│   │   │   └── shared/            # Shared components
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

* [x] Employee domain model (JPA)
* [x] Repository layer (Spring Data JPA)
* [x] Service layer with DTO mapping
* [x] REST controllers (explicit, no Spring Data REST)
* [x] Pagination & sorting via Pageable (generic query support)
* [x] Request validation (@Valid)
* [x] Global exception handling (@ControllerAdvice)
* [x] Consistent API error model (ApiError)
* [x] OpenAPI / Swagger documentation (tags, operations, responses)
* [x] Spring Boot Actuator (health, readiness, info)
* [x] Flyway database migrations (V1 initial schema)
* [x] API versioning strategy (/api/v1/*)

### Phase 2 — Security

* [ ] JWT authentication
* [ ] User & role model
* [ ] Method-level authorization
* [ ] Swagger UI security

### Phase 3 — Frontend

* [x] Angular application setup (standalone architecture)
* [x] Signals-based state management foundation
* [x] HTTP service layer with type-safe models
* [x] API base URL injection token pattern
* [x] Environment-ready configuration
* [ ] Employee list view with pagination
* [ ] Employee create/edit forms
* [ ] Employee detail view
* [ ] Delete confirmation dialogs
* [ ] JWT interceptor & guards
* [ ] Angular Material integration
* [ ] Error handling & user feedback
* [ ] Loading states & spinners

### Phase 4 — Deployment

* [ ] Container image (Dockerfile)
* [ ] Multi-stage builds
* [ ] CI/CD pipeline (GitHub Actions)
* [ ] Kubernetes manifests
* [ ] Helm charts
* [ ] Production deployment guide

---

## 🚀 Getting Started

### Prerequisites

* Java 25+
* Node.js 20+
* PostgreSQL 18+ (or use Docker)
* Maven 3.9+

### Backend Setup

```bash
cd backend
./mvnw spring-boot:run
```

API available at: `http://localhost:8080`
Swagger UI: `http://localhost:8080/swagger-ui.html`

### Frontend Setup

```bash
cd frontend
npm install
npm start
```

Application available at: `http://localhost:4200`

### Database (Docker)

```bash
docker compose up -d postgres
```

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