# 🎓 Full-Stack Learning Journey (Fedora)

This repository documents my step-by-step evolution into modern Enterprise development, leveraging the latest technologies and a professional DevOps workflow on Fedora Linux.

---

## 📂 Learning Modules

### [01_foundation/](./01_foundation/)

The core building blocks and environmental setup.

* **01_stack**: Deep dive into **Java 25** (Virtual Threads) and **Gradle 9.3**.
* **02_persistence**: Advanced **PostgreSQL 18** patterns, including triggers, optimistic locking, and relationship types (`@OneToOne`, `@OneToMany`, `@ManyToMany`).

### [02_system_integration/](./02_system_integration/)

🚀 **The Main Project: Employee Management System (EMS)**
This is a production-grade integration of all concepts into a single cohesive solution.

* **backend/**: **Spring Boot 4.0** (Spring 7) API using Project Loom.
* **frontend/**: **Angular 21** Signals-first standalone application.
* **Documentation**: Follows strict ADR (Architecture Decision Records) and a Layered Integration Testing Strategy.

### [03_gitlab/](./03_gitlab/)

DevOps and professional version control standards.

* Workflow integration using the **GitLab CLI (`glab`)**.
* Automated CI/CD pipeline definitions and repository management.

---

## 🛠️ Technical Stack Summary

| Component | Technology | Version | Key Feature |
| --- | --- | --- | --- |
| **OS** | Fedora Linux | Native | Podman-native infrastructure |
| **Runtime** | Java | 25 | Virtual Threads (Loom) |
| **Framework** | Spring Boot | 4.0+ | Spring Framework 7 |
| **Frontend** | Angular | 21+ | Signals-first / No NgModules |
| **Database** | PostgreSQL | 18 | Migrations via Flyway |
| **Build Tool** | Gradle | 9.3+ | Groovy DSL |

---

## 🚀 Quick Start

1. **Clone the repository**:
```bash
glab repo clone techthordev/learning

```


2. **Navigate to the Main Project**:
```bash
cd 02_system_integration/employee-management-system

```


3. **Start Infrastructure (Backend)**:
```bash
cd backend && podman-compose up -d

```


4. **Run Verified Tests**:
```bash
./gradlew clean test

```
