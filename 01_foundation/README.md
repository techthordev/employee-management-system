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

# Database Architecture & Design Analysis

This document provides a deep dive into the rationale behind our persistence layer, explaining the "Why" behind the script execution order, the security model, and how these choices support advanced JPA/Hibernate features.

---

## 🏗️ Execution Logic & Sequence

The scripts in `01_foundation/02_persistence/scripts/` follow a strict numeric prefix to satisfy PostgreSQL's initialization requirements and relational integrity.

### 1. Why the Numeric Order?

PostgreSQL executes scripts in `/docker-entrypoint-initdb.d/` in alphabetical order.

* **`01-init.sh`**: Must run first to create the database and the `springconnector` user. Without this, subsequent scripts would fail to connect or lack a schema owner.
* **`02-schema.sql`**: Defines the "Parent" entities (`department`, `project`) before "Child" entities (`employee`).
* **`04-employee-data.sql`**: Seeds data using IDs from the previously created tables.

### 2. Foreign Key Strategy

We use Foreign Keys (FK) not just for data integrity, but to enable **Advanced Hibernate Techniques**:

* **`ON DELETE SET NULL`**: In the `employee -> department` relation, if a department is deleted, the employee remains but the reference is nulled. This supports Hibernate's optional associations.
* **`ON DELETE CASCADE`**: Used in the `employee_projects` join table. This allows Hibernate to manage Many-to-Many collections efficiently without leaving orphaned rows in the link table.
* **One-to-One Constraints**: The `employee_profile` uses a `UNIQUE` constraint on `employee_id` to enforce a strict One-to-One mapping.

---

## 🔐 Security & Permission Model

### Why `springconnector`?

Instead of using the `postgres` superuser, we use a dedicated application user:

1. **Least Privilege Principle**: The app user only has access to the `employee_management` database.
2. **Schema Isolation**: We explicitly separate `public` (Business) and `auth` (Security) schemas.
3. **Default Privileges**: By using `ALTER DEFAULT PRIVILEGES`, we ensure that if Hibernate or Flyway creates a new table at runtime, the `springconnector` user automatically gains `ALL` permissions on it.

---

## 🔍 Validation & State Analysis

### Public Schema Verification

Your current state confirms the successful creation of all business entities:

```bash
# Verify Public Tables
podman exec -it ems-db-container psql -U springconnector -d employee_management -c "\dt public.*"

```

> **Observation**: All 5 business tables (`department`, `employee`, `employee_profile`, `employee_projects`, `project`) are present and owned correctly.

### Auth Schema Verification

To verify the RBAC (Role-Based Access Control) system, run:

```bash
# Verify Auth Tables
podman exec -it ems-db-container psql -U springconnector -d employee_management -c "\dt auth.*"

# Verify Role Mapping for 'susan'
podman exec -it ems-db-container psql -U springconnector -d employee_management -c "
SELECT u.username, r.name 
FROM auth.users u 
JOIN auth.user_roles ur ON u.id = ur.user_id 
JOIN auth.roles r ON ur.role_id = r.id 
WHERE u.username = 'susan';"

```

**Rationale**: This query confirms that the Many-to-Many relationship between users and roles in the `auth` schema is working.

---

## 🚀 Impact on Spring Boot 4.0 & Hibernate

* **Versioned Entities**: The `version` column in `department` and `employee` is designed for **Optimistic Locking** (`@Version` in JPA).
* **Auditability**: `created_at` and `updated_at` allow for automated auditing (via Hibernate Envers or JPA Listeners).
* **Virtual Threads**: By ensuring clean, non-blocking constraints and proper indexing, the DB is optimized for the high-concurrency throughput provided by Java 25 Virtual Threads.
