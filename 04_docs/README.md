# 🎓 Full-Stack Learning Journey (Fedora)

![Backend CI](https://github.com/techthordev/learning/actions/workflows/backend-ci.yml/badge.svg)

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
* Automated CI/CD mirror strategy and repository management.

---

## 🏗️ Architecture & DevOps

### Concurrency & Performance
* **Project Loom**: Full utilization of **Virtual Threads** for high-throughput I/O operations.
* **Spring Framework 7**: Leveraging the latest features of the Spring ecosystem for reactive and thread-efficient processing.

### Quality Gate (CI/CD)
The project implements a robust automated testing pipeline:
* **Infrastructure as Code**: Automated PostgreSQL 18 container orchestration within the CI environment.
* **Schema Validation**: Execution of native SQL initialization scripts (`01-init.sh` to `05-auth-data.sql`) before running tests.
* **Multi-Platform**: Primary development on GitHub with an automated mirror and secondary validation on GitLab.

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
| **IDEs** | Zed / IntelliJ | Latest | Modern coding experience |

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
Ensure Podman is running on your Fedora machine.
```bash
cd backend && podman-compose up -d

```


4. **Run Verified Tests**:
Execute the test suite used in the CI/CD pipeline.
```bash
./gradlew clean build

```



---

*Developed with ❤️ on Fedora Linux.*
