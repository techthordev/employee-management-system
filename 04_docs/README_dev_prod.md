# Environment Configuration (Dev & Prod)

This project uses Spring Profiles and separate environment files to clearly isolate
development and production configurations.

---

## Spring Profiles

The backend supports two profiles:

- `dev`
- `prod`

The active profile is controlled via:

```bash
SPRING_PROFILES_ACTIVE
````

---

## Configuration Files

### Base Configuration

`application.yml`

Contains shared configuration for all environments:

* JPA base setup
* Flyway base config
* Logging defaults
* JWT placeholders
* Datasource placeholders

---

### Development

`application-dev.yml`

Activated when:

```bash
SPRING_PROFILES_ACTIVE=dev
```

Dev characteristics:

* Verbose SQL logging
* Flyway clean enabled
* Debug logging
* Local database connection

---

### Production

`application-prod.yml`

Activated when:

```bash
SPRING_PROFILES_ACTIVE=prod
```

Prod characteristics:

* No SQL logging
* Flyway clean disabled
* Minimal logging
* Production database connection
* Secure JWT configuration

---

## Environment Files

Two separate environment files are used:

### `.env.dev`

Used for local development.

Example:

```
SPRING_PROFILES_ACTIVE=dev
SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/employee_management
```

---

### `.env.prod`

Used for production deployments.

If Spring runs inside Docker:

```
SPRING_PROFILES_ACTIVE=prod
SPRING_DATASOURCE_URL=jdbc:postgresql://postgres:5432/employee_management
```

If Spring runs directly on the server:

```
SPRING_PROFILES_ACTIVE=prod
SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/employee_management
```

---

## Docker (Postgres)

Database container:

```bash
docker compose --env-file .env.dev up -d
```

or

```bash
docker compose --env-file .env.prod up -d
```

The initialization scripts are located in:

```
01_foundation/02_persistence/scripts
```

They are executed automatically on first database initialization.

---

## Reset Database (Dev Only)

To completely reset the database:

```bash
docker compose down -v
docker compose up -d
```

⚠ This deletes the volume and all data.

---

## Why This Structure?

* Clear environment separation
* No accidental production configuration in development
* Secure secret handling
* Reproducible deployments
* Clean Docker setup
