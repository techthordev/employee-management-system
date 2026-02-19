# Employee Management System
### Modular Fullstack Architecture – Spring Boot • Angular • DevOps

![Java](https://img.shields.io/badge/Java-25-blue)
![Gradle](https://img.shields.io/badge/Gradle-9.x-green)
![License](https://img.shields.io/badge/License-MIT-lightgrey)
![Status](https://img.shields.io/badge/Status-Active%20Development-brightgreen)

A structured, production-oriented engineering project evolving from backend foundations to full system integration and professional DevOps workflows.

---

# 🏗 Architecture Overview

```mermaid
flowchart LR
    A[Angular Frontend] --> B[Spring Boot REST API]
    B --> C[(PostgreSQL)]
    B --> D[Flyway Migration]
    B --> E[JUnit Tests]
    F[GitHub] -->|Mirror Push| G[GitLab CI/CD]
``` id="m1v9qz"

---

# 📦 Repository Modules

## 01 – Foundation
Backend fundamentals:
- Spring Boot
- REST API design
- JPA / Hibernate
- Flyway
- YAML configuration
- Clean architecture

## 02 – System Integration
Fullstack integration:
- Angular frontend
- DTO mapping
- CORS configuration
- Logging preparation
- Error handling strategy

## 03 – DevOps
Professional version control & CI strategy:
- GitHub (Public Primary)
- GitLab (Automatic Mirror)
- SSH-only authentication
- Protected `main` branch
- CLI-first workflow (Fedora Linux)

Prepared for:
- GitLab CI/CD
- GitHub Actions
- Container builds (Podman / Buildah)

---

# 🔄 Repository Strategy

GitHub = Public Source of Truth  
GitLab = Automatic Push Mirror + CI  

Single push → synchronized platforms.

Secure SSH workflow.
No PAT tokens.
No HTTPS remotes.

---

# 🚀 Tech Stack

Backend:
- Java 25
- Spring Boot
- Gradle
- Flyway
- PostgreSQL

Frontend:
- Angular
- TypeScript

DevOps:
- Git
- GitHub
- GitLab
- Fedora Linux
- Podman / Buildah

---

# 📈 Development Status

- Modular architecture established
- CI/CD preparation in progress
- Containerization roadmap defined
- Continuous refinement ongoing

---

# 💡 Engineering Philosophy

- Atomic commits
- Modular growth
- Platform-neutral DevOps
- Production-oriented thinking
- CLI-driven workflow
