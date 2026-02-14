# 🎓 Full-Stack Learning Journey (Fedora)

This repository documents the step-by-step development of a modern Enterprise application, leveraging the latest technologies and a professional DevOps workflow on Fedora Linux.

---

## 📂 Project Structure

### [01_Foundation](https://www.google.com/search?q=./01_Foundation)

The core infrastructure and development environment.

* **[stack/](https://www.google.com/search?q=./01_Foundation/stack)**: Configuration for **Java 25** and **Gradle 9.3**, focusing on Virtual Threads and Structured Concurrency.
* **[persistence/](https://www.google.com/search?q=./01_Foundation/persistence)**: Containerized **PostgreSQL 18** setup via Podman.
* Implements all Hibernate relationship types: `@OneToOne`, `@OneToMany`, and `@ManyToMany`.
* Features automated `updated_at` triggers and Optimistic Locking (`version`).
* Secure initialization via shell-based user and database creation.



### [02_Backend](https://www.google.com/search?q=./02_Backend)

The server-side application logic built with **Spring Boot 4.0** (Spring Framework 7).

* High-performance processing using **Project Loom** (Virtual Threads).
* Secure identity management via a dedicated `auth` schema.

### [03_Frontend](https://www.google.com/search?q=./03_Frontend)

A modern user interface developed with **Angular 21**.

* **Signals-first** architecture for reactive state management.
* Standalone components to eliminate `NgModules`.

### [04_GitLab](https://www.google.com/search?q=./04_GitLab)

DevOps and version control standards for the project.

* Workflow integration using the **GitLab CLI (`glab`)**.
* Automated CI/CD pipeline definitions for building and testing.

### [05_System_Integration](https://www.google.com/search?q=./05_System_Integration)

🚀 The final production-grade **Employee Management System (EMS)**, integrating all previous modules into a cohesive solution.

---

## 🛠️ Technical Stack Summary

| Component | Technology | Version |
| --- | --- | --- |
| **OS** | Fedora Linux | Native |
| **Runtime** | Java | 25 |
| **Framework** | Spring Boot | 4.0+ |
| **Frontend** | Angular | 21+ |
| **Database** | PostgreSQL | 18 |
| **Container** | Podman | Native |
| **Build Tool** | Gradle (Groovy) | 9.3 |

---

## 🚀 Getting Started

1. **Clone the repository**:
```bash
git clone git@gitlab.com:techthordev/learning.git

```


2. **Initialize Infrastructure**:
Navigate to `01_Foundation/persistence` and start the database:
```bash
podman compose up -d

```


3. **Check Status**:
```bash
glab repo view
```
