# Podman / Docker Compose

## Setup
- Service: postgres
- Environment: POSTGRES_USER, POSTGRES_PASSWORD, POSTGRES_DB
- Healthcheck: pg_isready

## Best Practices
- Separate compose files for dev and prod
- Keep volumes and scripts organized
- Idempotent container setup
