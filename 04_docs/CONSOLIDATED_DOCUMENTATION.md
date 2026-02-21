# Employee Management System — Consolidated Documentation

## 1. Project Overview

This repository is organized as a learning-driven, production-oriented fullstack system:

- `01_foundation` — stack and persistence foundations.
- `02_system_integration/employee-management-system` — main fullstack project.
- `03_devops` — DevOps workflow and repository strategy.
- `04_docs` — consolidated documentation hub.

## 2. Current Architecture

### Backend

- Stack: Java 25, Spring Boot 4.x, Spring Security, Spring Data JPA, Flyway, PostgreSQL.
- Design: layered architecture (controller → service → repository → entity).
- API style: DTO-based request/response with validation.
- Security: JWT provider/filter + DB-backed `UserDetailsService`.

### Frontend

- Stack: Angular 21 standalone app.
- Status: scaffolded foundation with incremental feature integration expected.

### Database

- PostgreSQL schema split:
  - `public` for business entities.
  - `auth` for users/roles mappings.
- SQL bootstrap scripts in `01_foundation/02_persistence/scripts`.

## 3. Authentication Flow (implemented)

### Public Auth Endpoints

- `POST /auth/register` — create user with encoded password and default `ROLE_EMPLOYEE`.
- `POST /auth/login` — authenticate credentials and return Bearer JWT.

### Protected Endpoints

- All non-public API routes require JWT Bearer token.
- JWT is validated by `JwtAuthenticationFilter` + `JwtTokenProvider`.
- Method-level role guards are enabled (`@PreAuthorize`).

## 4. Local Setup (recommended baseline)

1. Start PostgreSQL/container infrastructure.
2. Ensure required environment variables are provided (`SPRING_DATASOURCE_URL`, `DB_APP_USER`, `DB_APP_PASSWORD`, `APP_JWT_SECRET`, `APP_JWT_EXPIRATION_MS`).
3. Run backend and verify `/docs` Swagger UI.
4. Run frontend with Angular CLI when needed.

## 5. Testing & Verification

- Backend tests are executed through Gradle wrapper.
- If wrapper download fails in restricted environments, use:
  - a preinstalled Gradle matching wrapper version, or
  - a network path that can access `services.gradle.org`.

## 6. Documentation Cleanup Decisions

- Centralized all navigation/documentation entry under `04_docs/README_INDEX.md`.
- Moved previous ad-hoc README indexing away from root/docs scattering.
- Kept module-local READMEs for component-specific context.

## 7. Known Inconsistencies Fixed

- Unified naming to `03_devops` (replacing stale references to `03_gitlab`).
- Centralized docs entry path to `04_docs/README_INDEX.md`.
- Consolidated prior analysis/index artifacts into this structured set.

## 8. Next Recommended Steps

1. Add API request/response examples for auth and core CRUD resources.
2. Add a concise troubleshooting section (DB connection, JWT secret length, Gradle proxy).
3. Introduce a changelog section for architecture/security decisions.
