# Employee Management System

### Modular Fullstack Architecture – Spring Boot • Angular • DevOps

![Java](https://img.shields.io/badge/Java-25-007396?logo=openjdk&logoColor=white)

![Gradle](https://img.shields.io/badge/Gradle-9.x-02303A?logo=gradle&logoColor=white)

![Spring Boot](https://img.shields.io/badge/Spring_Boot-4.x-6DB33F?logo=spring-boot&logoColor=white)

![Angular](https://img.shields.io/badge/Angular-21-DD0031?logo=angular&logoColor=white)

![License](https://img.shields.io/badge/License-MIT-lightgrey)

A structured, production-oriented engineering project evolving from backend foundations to full system integration and professional DevOps workflows.

---

# 🏗 Architecture Overview

```mermaid
flowchart LR
    A[Angular Frontend] --> B[Spring Boot Backend]
    B --> C[PostgreSQL Database]
    B --> D[Flyway Migrations]
    B --> E[JUnit Tests]
    F[GitHub Public Repo] --> G[GitLab Mirror + CI]
````

---

# 📦 Repository Modules

## 01 – Foundation

Backend fundamentals:

* Spring Boot
* REST API design
* JPA / Hibernate
* Flyway
* YAML configuration
* Clean architecture

## 02 – System Integration

Fullstack integration:

* Angular frontend
* DTO mapping
* CORS configuration
* Logging preparation
* Error handling strategy

## 03 – DevOps

Professional version control & CI strategy:

* GitHub (Public Primary)
* GitLab (Automatic Mirror)
* SSH-only authentication
* Protected `main` branch
* CLI-first workflow (Fedora Linux)

Prepared for:

* GitLab CI/CD
* GitHub Actions
* Container builds (Podman / Buildah)

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

## Backend

* Java 25
* Spring Boot
* Gradle
* Flyway
* PostgreSQL

## Frontend

* Angular
* TypeScript

## DevOps

* Git
* GitHub
* GitLab
* Fedora Linux
* Podman / Buildah

---

# 📈 Development Status

* Modular architecture established
* CI/CD preparation in progress
* Containerization roadmap defined
* Continuous refinement ongoing

---

# 💡 Engineering Philosophy

* Atomic commits
* Modular growth
* Platform-neutral DevOps
* Production-oriented thinking
* CLI-driven workflow
