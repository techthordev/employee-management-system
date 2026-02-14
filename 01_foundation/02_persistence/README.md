# 🗄️ Database Infrastructure Guide

This guide covers the manual setup, technical rationale, and verification of the PostgreSQL 18 environment for the Employee Management System.

## 🛠️ Execution Order & Technical Rationale

The scripts in `01_foundation/02_persistence/scripts/` follow a strict numeric sequence to satisfy relational dependencies and security requirements.

### 1. `01-init.sh` - Infrastructure & Permission Layer

* **Why**: PostgreSQL's `docker-entrypoint` executes scripts alphabetically. This shell script must run first to create the database, the `springconnector` user, and the `auth` schema.
* **Advanced Permissions**: We use `ALTER DEFAULT PRIVILEGES`. This ensures that if Hibernate or Flyway creates new tables in the future, the app-user automatically receives full permissions without manual intervention.
* **Ownership**: Transferring schema ownership to the app-user allows for seamless schema migrations.

### 2. `02-schema.sql` - Business Domain

* **Why**: Defines the core entities.
* **FK Strategy**: `department` and `project` are created before `employee`.
* **Hibernate Optimization**: We include a `version` column for **Optimistic Locking** (`@Version`) and `ON DELETE SET NULL` constraints to handle optional associations gracefully at the database level.

### 3. `03-auth.sql` - Security Domain (RBAC)

* **Why**: Strikt separation of concerns. Security data is isolated in the `auth` schema.
* **Advanced Mapping**: Implements a Many-to-Many relationship between `users` and `roles` via `user_roles`. This structure is optimized for Spring Security's `UserDetailsService`.

### 4. `04-employee-data.sql` & `05-auth-data.sql` - Data Seeding

* **Constraint Handling**: These scripts use sub-queries (e.g., `SELECT id FROM ...`) to resolve foreign keys dynamically. This prevents hard-coded ID errors during re-initialization.
* **Security**: Passwords in `05-auth-data.sql` are stored as BCrypt hashes.

---

## 🚀 Commands & Integrity Checks

### Launch & Monitoring

```bash
# Start infrastructure
podman compose up -d

# Watch Initialization Logs (Wait for "PostgreSQL init process complete")
podman logs -f ems-db-container

```

### 🔍 Verification Queries (CLI)

Verify the state of the system using `podman exec`. These queries confirm that permissions, schemas, and data relations are intact.

#### 1. Schema & Ownership Check

Ensures all tables exist and are accessible by the `springconnector` user.

```bash
# List all tables in both schemas
podman exec -it ems-db-container psql -U springconnector -d employee_management -c "\dt *.*"

```

#### 2. Business Logic Check (Public Schema)

Verifies that employees are correctly linked to departments.

```bash
podman exec -it ems-db-container psql -U springconnector -d employee_management -c "
SELECT e.first_name, e.last_name, d.name as department 
FROM public.employee e 
JOIN public.department d ON e.department_id = d.id;"

```

#### 3. Security Integrity Check (Auth Schema)

Verifies the RBAC (Role-Based Access Control) mapping. This is critical for Spring Security.

```bash
# Detailed check for 'susan' and her assigned roles
podman exec -it ems-db-container psql -U springconnector -d employee_management -c "
SELECT u.username, r.name as role_name 
FROM auth.users u 
JOIN auth.user_roles ur ON u.id = ur.user_id 
JOIN auth.roles r ON ur.role_id = r.id 
ORDER BY u.username;"

```

### Hard Reset

To wipe all data, volumes, and force a fresh execution of the init-scripts:

```bash
podman compose down -v && podman compose up -d

```
