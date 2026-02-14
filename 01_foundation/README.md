# Module 01: Foundation

This module establishes the core infrastructure and development environment for the entire project. It is divided into the language runtime and the persistence layer.

## 📂 Sub-Modules

### [stack/](./stack)
* **Java 25:** Configuration for the latest JDK featuring Virtual Threads and Structured Concurrency.
* **Gradle 9.3:** Build automation using Groovy DSL.

### [persistence/](./persistence)
* **PostgreSQL 18:** Containerized database management.
* **Schema Design:** Implementation of complex Hibernate relations (1:1, 1:N, N:M).
* **Automation:** Bash-based initialization for secure user management.

## 🛠️ Getting Started
1. Ensure your `.env` file in the `persistence/` folder is configured.
2. Run the infrastructure:
   ```bash
   cd persistence && podman compose up -d


---

### 2. 01_Foundation/persistence: README
Hier dokumentieren wir spezifisch, wie die Datenbank-Infrastruktur funktioniert.

**Pfad:** `01_Foundation/persistence/README.md`
```markdown
# Persistence Layer (PostgreSQL 18)

Standardized database environment using Podman Compose, optimized for Fedora.

## 🗄️ Database Architecture
* **Public Schema:** Contains business entities (Employees, Departments, Projects).
* **Auth Schema:** Dedicated schema for security, users, and roles.
* **Relationships:**
    * **1:1**: Employee ↔ Profile.
    * **1:N**: Department ↔ Employee.
    * **N:M**: Employee ↔ Project via Join Table.

## ⚙️ Automated Setup
The `scripts/01-init.sh` script automates the following at first launch:
1. Creates the application user (`DB_APP_USER`) and database (`POSTGRES_DB`) using environment variables.
2. Sets correct ownership and schema permissions.
3. Executes schema and data seeding in the correct order.

## 🚀 Commands
* **Start:** `podman compose up -d`
* **Stop:** `podman compose down`
* **Reset:** Use `psql -f scripts/reset.sql` to truncate business data while keeping the schema.