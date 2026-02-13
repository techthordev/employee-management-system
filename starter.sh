#!/bin/bash
# Full Learning Directory Starter Script for Spring Boot (Java) + Angular + PostgreSQL + Podman
# Directory: learning/
set -e

BASE_DIR="learning"

echo "Creating full learning directory structure..."

# 01_Gradle
mkdir -p "$BASE_DIR/01_Gradle"

cat > "$BASE_DIR/01_Gradle/README.md" <<'EOF'
# Gradle Setup (Java)

## Build & Run
- Build project: ./gradlew build
- Run project: ./gradlew bootRun

## Best Practices
- Use Gradle Groovy DSL (build.gradle) for new projects
- Keep build scripts clean and organized
- Manage dependencies carefully
EOF

# 02_PostgreSQL
mkdir -p "$BASE_DIR/02_PostgreSQL/init-scripts"

cat > "$BASE_DIR/02_PostgreSQL/README.md" <<'EOF'
# PostgreSQL Setup

## Users & Database
- Create user: rustconnector
- Database: ems_db

## Schemas & Tables
- Default schema: public
- Auth schema: auth
- Tables: employee, users, roles, user_roles

## Best Practices
- Keep init scripts idempotent
- Version SQL scripts with Git
- Sample data only for development
EOF

cat > "$BASE_DIR/02_PostgreSQL/init-scripts/01-create-user-db.sql" <<'EOF'
-- PostgreSQL init script example
DO $$
BEGIN
  IF NOT EXISTS (SELECT FROM pg_roles WHERE rolname = 'rustconnector') THEN
    CREATE USER rustconnector WITH PASSWORD 'rustconnector';
  END IF;
END $$;

DO $$
BEGIN
  IF NOT EXISTS (SELECT FROM pg_database WHERE datname = 'ems_db') THEN
    CREATE DATABASE ems_db OWNER rustconnector;
  END IF;
END $$;
EOF

# 03_DevOps
mkdir -p "$BASE_DIR/03_DevOps/compose-files"

cat > "$BASE_DIR/03_DevOps/README.md" <<'EOF'
# Podman / Docker Compose

## Setup
- Service: postgres
- Environment: POSTGRES_USER, POSTGRES_PASSWORD, POSTGRES_DB
- Healthcheck: pg_isready

## Best Practices
- Separate compose files for dev and prod
- Keep volumes and scripts organized
- Idempotent container setup
EOF

cat > "$BASE_DIR/03_DevOps/compose-files/docker-compose.yml" <<'EOF'
version: '3.9'
services:
  postgres:
    image: postgres:18-alpine
    container_name: ems-db-container
    environment:
      POSTGRES_USER: postgres
      POSTGRES_PASSWORD: postgres
      POSTGRES_DB: ems_db
    ports:
      - "5432:5432"
    volumes:
      - ems_db_data:/var/lib/postgresql/data
volumes:
  ems_db_data:
EOF

# 04_SpringBoot
mkdir -p "$BASE_DIR/04_SpringBoot/security"
mkdir -p "$BASE_DIR/04_SpringBoot/swagger"
mkdir -p "$BASE_DIR/04_SpringBoot/entities"
mkdir -p "$BASE_DIR/04_SpringBoot/repositories"
mkdir -p "$BASE_DIR/04_SpringBoot/services"

cat > "$BASE_DIR/04_SpringBoot/README.md" <<'EOF'
# Spring Boot (Java)

## Project Structure
- entities/ -> JPA Entities
- repositories/ -> Spring Data Repositories
- services/ -> Business Logic
- controllers/ -> REST Endpoints
- security/ -> SecurityConfig, UserDetailsService
- swagger/ -> OpenAPI / Springdoc

## Security
- Basic Auth or JWT
- BCryptPasswordEncoder recommended
- Test endpoints with Postman or Swagger UI

## Build & Run
- ./gradlew build
- ./gradlew bootRun
EOF

# 05_Angular
mkdir -p "$BASE_DIR/05_Angular/snippets"

cat > "$BASE_DIR/05_Angular/README.md" <<'EOF'
# Angular

## Project Structure
- components/ -> UI components
- services/ -> HttpClient services
- models/ -> Interfaces / DTOs
- routing/ -> RouterModule configuration

## Best Practices
- Use services for API calls
- Observables & async pipes
- Forms & validation
- Reusable modules and components
EOF

cat > "$BASE_DIR/05_Angular/snippets/employee-service.ts" <<'EOF'
@Injectable({providedIn: 'root'})
export class EmployeeService {
  constructor(private http: HttpClient) {}
  list(): Observable<Employee[]> {
    return this.http.get<Employee[]>('/api/employees');
  }
}
EOF

# 06_Projects
mkdir -p "$BASE_DIR/06_Projects/EMS-Project/backend"
mkdir -p "$BASE_DIR/06_Projects/EMS-Project/frontend"

cat > "$BASE_DIR/06_Projects/EMS-Project/README.md" <<'EOF'
# EMS Project

## Overview
- Fullstack project: Spring Boot backend (Java) + Angular frontend
- PostgreSQL database with public and auth schemas
- Docker/Podman compose for dev environment

## Lessons Learned
- Test endpoints after creating controllers
- Keep init SQL scripts idempotent
- Document API early with Swagger
EOF

# 07_Assets
mkdir -p "$BASE_DIR/assets/images"

echo "Creating ZIP file..."
zip -r learning-starter.zip "$BASE_DIR"

echo "Done! ZIP created: learning-starter.zip"
