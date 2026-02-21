# 🎓 Full-Stack Learning Journey (Fedora)

![Backend CI](https://github.com/techthordev/learning/actions/workflows/backend-ci.yml/badge.svg)

This repository documents my step-by-step evolution into modern Enterprise development,
leveraging the latest technologies and a professional DevOps workflow on Fedora Linux.

---

## 📂 Learning Modules

### [01_foundation/](./01_foundation/)

Core building blocks and environmental setup.

- **01_stack**: Java 25 (Virtual Threads) and Gradle 9.3.
- **02_persistence**: PostgreSQL 18 patterns, Flyway migrations, JPA relationship types.

### [02_system_integration/](./02_system_integration/)

🚀 **The Main Project: Employee Management System (EMS)**
Production-grade integration of all concepts into a single cohesive solution.

- **backend/**: Spring Boot 4.0 (Spring 7) REST API with JWT Security.
- **frontend/**: Angular 21 Signals-first standalone application.

### [03_devops/](./03_devops/)

DevOps and professional version control standards.

- CI/CD pipeline with GitHub Actions and GitLab mirror.
- Branch strategy, release and versioning policy.

### [04_docs/](./04_docs/)

📚 Consolidated documentation hub.

- Start here: [`04_docs/README_INDEX.md`](./04_docs/README_INDEX.md)

---

## 🛠️ Technical Stack

| Component     | Technology   | Version | Key Feature                     |
|---------------|--------------|---------|----------------------------------|
| **OS**        | Fedora Linux | Native  | Podman-native infrastructure     |
| **Runtime**   | Java         | 25      | Virtual Threads (Loom)           |
| **Framework** | Spring Boot  | 4.0+    | Spring Framework 7               |
| **Frontend**  | Angular      | 21+     | Signals-first / No NgModules     |
| **Database**  | PostgreSQL   | 18      | Migrations via Flyway            |
| **Build**     | Gradle       | 9.3+    | Groovy DSL                       |
| **Security**  | JWT (jjwt)   | 0.13+   | Stateless Bearer authentication  |
