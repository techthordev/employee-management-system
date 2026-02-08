Alles klar 👍
Hier ist die **vollständig angepasste README.md**, **konsistent mit deinem aktuellen Stand**
(**Flyway vorbereitet, aber derzeit deaktiviert**), **bereit für Copy & Paste**.

---

````md
# Employee Management System (Hybrid Edition)

A modern full-stack application built with **Spring Boot 4** and **Java 25 (LTS)**.  
This project showcases a hybrid backend architecture, combining a versioned REST API for client applications with an integrated Admin Backoffice.

---

## 🏁 Overview

- **Backend:** Spring Boot 4.x with Java 25 (LTS)
- **Database:** PostgreSQL (schema initialized via SQL init scripts, Flyway prepared)
- **REST API:** Exposed under `/v1/` (documented via OpenAPI/Swagger)
- **Admin UI:** Built with **Vaadin 25 (Flow)**, available at `/api/admin/`
- **IDE:** Developed using Zed and IntelliJ IDEA on Fedora Linux

---

## 🚀 Features

- **RESTful API:** Complete CRUD operations for employee management.
- **API Versioning:** Path-based versioning with clean controller mappings.
- **Vaadin Admin Panel:** Secure, server-side rendered dashboard for IT Support and Backoffice tasks.
- **Type Safety:** End-to-end type safety using DTOs and modern Java features.
- **Database Initialization:** Schema and test data initialized via SQL scripts.
- **Flyway Ready:** Migration setup prepared for future activation.
- **Hybrid Architecture:** REST API and Admin UI running side by side without routing conflicts.

---

## 🛠️ Technology Stack

| Layer           | Technology |
|----------------|------------|
| Runtime        | Java 25 (LTS) |
| Framework      | Spring Boot 4.0.2 |
| UI (Admin)     | Vaadin 25.0.4 (Flow) |
| Persistence    | Spring Data JPA / Hibernate |
| Database       | PostgreSQL 18+ |
| Database Setup | SQL init scripts (Flyway prepared) |
| Documentation  | SpringDoc OpenAPI (Swagger) |

---

## 🔢 API Versioning Strategy

This project uses **path-based API versioning** implemented according to **Spring Framework 7** best practices.

### Versioning Approach

- API versions are exposed via URL prefixes (e.g. `/v1`)
- Versioning is applied **centrally** using a custom annotation
- Controllers remain clean and free of hard-coded version paths

Example endpoints:

```http
GET    /v1/employees
POST   /v1/employees
GET    /v1/employees/{id}
PUT    /v1/employees/{id}
DELETE /v1/employees/{id}
````

### Implementation Details

API versioning is implemented using:

* A custom `@ApiVersion` annotation
* A centralized `WebMvcConfigurer` with `PathMatchConfigurer`
* No deprecated APIs or legacy path matchers

Only controllers annotated with `@ApiVersion(1)` are automatically mapped under the `/v1` path.

This enables:

* Parallel support for multiple API versions (`/v1`, `/v2`, …)
* Zero breaking changes for existing clients
* Clean, maintainable controller code
* Clear separation of API evolution concerns

---

## 📘 Swagger / OpenAPI Integration

* Swagger automatically detects versioned endpoints
* API documentation is generated with the correct `/v1` prefix
* No manual OpenAPI configuration is required for versioning

Access the API documentation at:

```text
http://localhost:8080/api/docs
```

---

## 🧩 Hybrid Architecture (REST + Vaadin)

This application follows a **hybrid architecture**:

### REST API

Designed for external clients such as:

* Web frontends
* Mobile applications
* Third-party integrations

### Vaadin Admin Backoffice

A server-side rendered UI for:

* Internal administration
* IT support tasks
* Backoffice operations

Both systems coexist **without routing conflicts**:

| Component       | Base Path       |
| --------------- | --------------- |
| REST API        | `/v1/**`        |
| Swagger UI      | `/api/docs`     |
| Vaadin Admin UI | `/api/admin/**` |

Vaadin routes are completely independent from REST controllers and are **not affected** by API versioning.

---

## 🗄️ Database Strategy

At the moment, the database schema is initialized using SQL scripts
(e.g. via container init scripts or manual execution).

Flyway is already included in the project dependencies and the migration
structure is prepared, but **Flyway is currently disabled** and not yet
used to manage schema evolution.

This allows:

* Simple and explicit control over the database during early development
* Easy inspection and modification of SQL during prototyping
* A smooth transition to Flyway-based migrations at a later stage

### Planned Migration Strategy

In a later phase, the project will switch to Flyway-managed migrations,
using versioned migration files (`V1__`, `V2__`, …) and repeatable
migrations for development data.

This transition does not require structural changes to the application.

---

## 📦 Project Goals

This project is designed as:

* A **reference architecture** for modern Spring Boot 4 applications
* A demonstration of **clean and scalable API versioning**
* A real-world example of **REST + server-side UI** in a single codebase
* A foundation for enterprise-ready backend systems

---

## 🔮 Future Extensions

The architecture explicitly supports:

* API v2 / v3 without breaking existing clients
* OAuth2 / OpenID Connect authentication
* Role-based access control for the Admin Backoffice
* Contract testing per API version
* External client SDK generation via OpenAPI

---

## ⚙️ Getting Started

### Prerequisites

* JDK 25 (LTS)
* PostgreSQL 18+
* Node.js (required by Vaadin for frontend bundling)

### Installation

1. Clone the repository.
2. Configure your database connection in
   `src/main/resources/application.properties`.
3. Initialize the database schema using the provided SQL scripts.
4. Run the application:

```bash
./mvnw spring-boot:run
```

---

## 🌐 Accessing the Application

* **REST API Documentation:**
  `http://localhost:8080/api/docs`

* **Vaadin Admin Backoffice:**
  `http://localhost:8080/api/admin/`

---

## 📝 License

Distributed under the **MIT License**.
See `LICENSE` for more information.