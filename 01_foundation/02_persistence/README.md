# 🗄️ Database Infrastructure Guide

This guide covers the manual setup and maintenance of the PostgreSQL 18 environment for the Employee Management System.

## 🛠️ Execution Order (Scripts)
The following scripts in `01_foundation/02_persistence/scripts/` must run without errors:

1.  **`01-init.sh`**: Bootstraps the environment. Creates `DB_APP_USER`, schemas (`public`, `auth`), and configures recursive `GRANT ALL` permissions and `ALTER DEFAULT PRIVILEGES` for future objects.
2.  **`02-schema.sql`**: Business logic tables. *Note: `project` and `department` must precede `employee` and `employee_projects` due to FK constraints.*
3.  **`03-auth-schema.sql`**: Security infrastructure within the `auth` schema (users, roles, and mapping tables).
4.  **`04-employee-data.sql`**: Master data for employees, departments, and project assignments.
5.  **`05-auth-data.sql`**: RBAC credentials. Includes `ROLE_ADMIN`, `ROLE_MANAGER`, and `ROLE_EMPLOYEE` assignments.

## 🚀 Commands

### Launch & Check
```bash
# Start infrastructure
podman compose up -d

# Watch Initialization Logs (Look for "init process complete")
podman logs -f ems-db-container

Integrity Checks (CLI)

Verify that tables exist and permissions are active for the app user:
Bash

# List all tables
podman exec -it ems-db-container psql -U springconnector -d employee_management -c "\dt *.*"

# Verify Role Assignments
podman exec -it ems-db-container psql -U springconnector -d employee_management -c "SELECT u.username, r.name FROM auth.users u JOIN auth.user_roles ur ON u.id = ur.user_id JOIN auth.roles r ON ur.role_id = r.id;"

Hard Reset (Wipe Data & Volumes)

Use this command if you change SQL schemas or need a completely clean slate:
Bash

podman compose down -v && podman compose up -d

Status: Infrastructure verified for Spring Boot 4.0.x / Spring Framework 7.
