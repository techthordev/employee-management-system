# Employee Management System (Hybrid Edition)

A modern full-stack application built with **Spring Boot 4** and **Java 25**. This project serves as a showcase for a hybrid frontend architecture, providing a REST API for client applications and a powerful integrated Admin Backoffice.

## 🏁 Overview

- **Backend:** Spring Boot 4.x with Java 25
- **Database:** PostgreSQL (with Flyway migrations)
- **REST API:** Exposed at `/api/` (documented via OpenAPI/Swagger)
- **Admin UI:** Built with **Vaadin 25 (Flow)**, available at `/api/admin/`
- **IDE:** Developed using Zed and IntelliJ IDEA on Fedora Linux

## 🚀 Features

* **RESTful API:** Complete CRUD operations for employee management.
* **Vaadin Admin Panel:** A secure, server-side rendered dashboard for IT Support and Backoffice tasks.
* **Type Safety:** End-to-end type safety using DTOs and modern Java features.
* **Database Migrations:** Version-controlled schema changes using Flyway.

## 🛠️ Technology Stack

| Layer | Technology |
| :--- | :--- |
| Runtime | Java 25 (LTS preview features) |
| Framework | Spring Boot 4.0.2 |
| UI (Admin) | Vaadin 25.0.4 |
| Persistence | Spring Data JPA / Hibernate |
| Database | PostgreSQL |
| Documentation | SpringDoc OpenAPI (Swagger) |

## ⚙️ Getting Started

### Prerequisites
- JDK 25
- PostgreSQL 18+
- Node.js (required by Vaadin for frontend bundling)

### Installation
1. Clone the repository:

 2. Configure your database in `src/main/resources/application.properties`.
3. Run the application:
```bash
./mvnw spring-boot:run
```

### Accessing the App

* **REST API Docs:** `http://localhost:8080/api/docs`
* **Admin Backoffice:** `http://localhost:8080/api/admin/`

## 📝 License

Distributed under the MIT License.
