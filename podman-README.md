# Employee Management System - Podman Guide

## Overview

This guide covers running the Employee Management System using Podman on Fedora and other Linux distributions with SELinux enabled.

## Table of Contents

- [Prerequisites](#prerequisites)
- [Quick Start](#quick-start)
- [Configuration](#configuration)
- [Podman-Specific Details](#podman-specific-details)
- [Database Initialization](#database-initialization)
- [Common Issues & Solutions](#common-issues--solutions)
- [Tips & Tricks](#tips--tricks)
- [Managing the Database](#managing-the-database)
- [Cleanup & Reset](#cleanup--reset)

---

## Prerequisites

### Required Software

```bash
# Install podman and podman-compose
sudo dnf install podman podman-compose

# Verify installation
podman --version
podman-compose --version
```

### System Requirements

- **OS**: Fedora 38+, RHEL 8+, or any Linux with Podman support
- **SELinux**: Enabled (typical for Fedora/RHEL)
- **User**: Rootless Podman (no sudo required)

---

## Quick Start

### 1. Project Structure

Ensure your project has this structure:

```
employee-management-system/
├── compose.yml
└── database/
    ├── init/
    │   └── 01-init.sql
    ├── schema/
    │   └── 02-schema.sql
    └── data/
        └── 03-data.sql
```

### 2. Start the Database

```bash
# Start in detached mode
podman compose up -d

# View logs
podman compose logs -f

# Check status
podman compose ps
```

### 3. Verify Database is Ready

```bash
# Wait for healthcheck to pass
podman compose ps

# Connect to database
podman exec -it employee-management-db psql -U postgres -d employee_management

# Inside psql:
\dt                        # List tables
SELECT * FROM employees;   # View data
\du                        # List users
\q                         # Quit
```

---

## Configuration

### Environment Variables

Create a `.env` file in the project root (optional):

```bash
# Database credentials
POSTGRES_PASSWORD=your_secure_password
POSTGRES_PORT=5432
```

**Default values** (used if `.env` not present):
- `POSTGRES_PASSWORD`: `postgres`
- `POSTGRES_PORT`: `5432`

### compose.yml

```yaml
services:
  postgres:
    image: postgres:18-alpine
    container_name: employee-management-db
    userns_mode: "keep-id"  # Podman-specific: preserves user namespace
    environment:
      POSTGRES_USER: postgres
      POSTGRES_PASSWORD: ${POSTGRES_PASSWORD:-postgres}
      POSTGRES_DB: employee_management
    ports:
      - "${POSTGRES_PORT:-5432}:5432"
    volumes:
      - ./database/init/01-init.sql:/docker-entrypoint-initdb.d/01-init.sql:z
      - ./database/schema/02-schema.sql:/docker-entrypoint-initdb.d/02-schema.sql:z
      - ./database/data/03-data.sql:/docker-entrypoint-initdb.d/03-data.sql:z
      - postgres-data:/var/lib/postgresql/data
    healthcheck:
      test: ["CMD-SHELL", "pg_isready -U postgres -d employee_management"]
      interval: 10s
      timeout: 5s
      retries: 5
    restart: unless-stopped

volumes:
  postgres-data:
```

---

## Podman-Specific Details

### Rootless Containers

Podman runs containers **without root privileges** by default. This is more secure but requires special configuration for file permissions.

### SELinux Labels

On Fedora/RHEL, SELinux is enabled by default. Volume mounts require proper labels:

- **`:z` (lowercase)**: Shared label - multiple containers can access
- **`:Z` (uppercase)**: Private label - exclusive to this container
- **No label**: May fail with permission errors

**Our configuration**:
```yaml
volumes:
  # SQL files: :z (shared, read-only)
  - ./database/init/01-init.sql:/docker-entrypoint-initdb.d/01-init.sql:z
  
  # Data volume: no label needed (named volumes work differently)
  - postgres-data:/var/lib/postgresql/data
```

### User Namespace Mode

```yaml
userns_mode: "keep-id"
```

**What it does**:
- Maps your host user ID to the container user
- Prevents permission errors with volumes
- Required for rootless Podman with persistent volumes

**Alternative approach** (if `keep-id` doesn't work):
```yaml
user: "999:999"  # postgres user UID/GID in container
```

### Podman vs Docker Compose

Podman uses an external Docker Compose provider. You may see this message:

```
>>>> Executing external compose provider "/usr/libexec/docker/cli-plugins/docker-compose"
```

This is **normal** and can be suppressed with:
```bash
export COMPOSE_PROVIDER=podman-compose
```

Or use native `podman-compose`:
```bash
podman-compose up -d
```

---

## Database Initialization

### Initialization Order

PostgreSQL executes scripts in `/docker-entrypoint-initdb.d/` in **alphabetical order**:

1. `01-init.sql` - Create users and set permissions
2. `02-schema.sql` - Create tables and schemas
3. `03-data.sql` - Insert initial data

### Important Notes

⚠️ **Init scripts only run on FIRST START** when the data volume is empty!

To re-run initialization:
```bash
podman compose down -v  # Remove volumes
podman compose up -d    # Start fresh
```

### Example SQL Files

**database/init/01-init.sql**:
```sql
CREATE USER employee_user WITH PASSWORD 'employee_pass';
GRANT ALL PRIVILEGES ON DATABASE employee_management TO employee_user;

\c employee_management
GRANT ALL PRIVILEGES ON SCHEMA public TO employee_user;
ALTER DEFAULT PRIVILEGES IN SCHEMA public GRANT ALL ON TABLES TO employee_user;
```

**database/schema/02-schema.sql**:
```sql
\c employee_management

CREATE TABLE employees (
    id SERIAL PRIMARY KEY,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    department VARCHAR(100),
    hire_date DATE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

**database/data/03-data.sql**:
```sql
\c employee_management

INSERT INTO employees (first_name, last_name, email, department, hire_date) VALUES
('Max', 'Mustermann', 'max@example.com', 'IT', '2024-01-15'),
('Anna', 'Schmidt', 'anna@example.com', 'HR', '2024-02-01');
```

---

## Common Issues & Solutions

### 1. Permission Denied Errors

**Symptom**:
```
mkdir: can't create directory '/var/lib/postgresql/18/': Permission denied
```

**Solutions**:

a) **Add `userns_mode: "keep-id"`** (recommended):
```yaml
services:
  postgres:
    userns_mode: "keep-id"
```

b) **Specify user explicitly**:
```yaml
services:
  postgres:
    user: "999:999"
```

c) **Check SELinux contexts**:
```bash
ls -Z database/
# Should show something like: unconfined_u:object_r:container_file_t:s0
```

### 2. Init Scripts Not Running

**Symptom**: Tables/users not created

**Cause**: Volume already exists from previous run

**Solution**:
```bash
# Complete cleanup
podman compose down -v
podman volume ls  # Verify volume removed
podman compose up -d
```

### 3. Port Already in Use

**Symptom**:
```
Error: failed to expose ports: address already in use
```

**Solutions**:

a) **Check what's using port 5432**:
```bash
sudo ss -tulpn | grep 5432
```

b) **Change port in `.env`**:
```bash
POSTGRES_PORT=5433
```

c) **Stop conflicting service**:
```bash
sudo systemctl stop postgresql  # If system postgres is running
```

### 4. Container Exits Immediately

**Check logs**:
```bash
podman compose logs postgres
```

**Common causes**:
- Invalid SQL syntax in init files
- Wrong file paths
- Missing environment variables

### 5. Cannot Connect from Host

**Symptom**: Connection refused when trying to connect

**Solutions**:

a) **Check container is running**:
```bash
podman compose ps
```

b) **Check health status**:
```bash
podman inspect employee-management-db | grep Health -A 10
```

c) **Verify port binding**:
```bash
podman port employee-management-db
```

d) **Connect using localhost** (not 127.0.0.1 on some systems):
```bash
psql -h localhost -p 5432 -U postgres -d employee_management
```

---

## Tips & Tricks

### Podman Aliases

Add to `~/.bashrc` or `~/.zshrc`:

```bash
alias dc='podman compose'
alias dcu='podman compose up -d'
alias dcd='podman compose down'
alias dcl='podman compose logs -f'
alias dcp='podman compose ps'
alias dcr='podman compose down -v && podman compose up -d'  # Full reset
```

Usage:
```bash
dcu   # Start services
dcl   # Follow logs
dcr   # Complete reset
```

### Development Workflow

```bash
# Make changes to SQL files
vim database/schema/02-schema.sql

# Reset database to apply changes
podman compose down -v
podman compose up -d

# Watch logs for errors
podman compose logs -f
```

### Backup Database

```bash
# Create backup
podman exec employee-management-db pg_dump -U postgres employee_management > backup.sql

# Restore backup
podman exec -i employee-management-db psql -U postgres -d employee_management < backup.sql
```

### Execute SQL Directly

```bash
# Single command
podman exec employee-management-db psql -U postgres -d employee_management -c "SELECT COUNT(*) FROM employees;"

# From file
podman exec -i employee-management-db psql -U postgres -d employee_management < query.sql
```

### View Container Resource Usage

```bash
podman stats employee-management-db
```

### Inspect Container Details

```bash
# Full inspection
podman inspect employee-management-db

# Just IP address
podman inspect employee-management-db | grep IPAddress

# Just environment variables
podman inspect employee-management-db | grep -A 20 Env
```

### Auto-start on System Boot

```bash
# Generate systemd service
podman generate systemd --new --files --name employee-management-db

# Move to user systemd directory
mkdir -p ~/.config/systemd/user
mv container-employee-management-db.service ~/.config/systemd/user/

# Enable and start
systemctl --user enable container-employee-management-db.service
systemctl --user start container-employee-management-db.service

# Check status
systemctl --user status container-employee-management-db.service
```

---

## Managing the Database

### Connect to Database

```bash
# Using podman exec
podman exec -it employee-management-db psql -U postgres -d employee_management

# Using psql from host (requires postgresql client)
psql -h localhost -p 5432 -U postgres -d employee_management
```

### Common psql Commands

```sql
-- List databases
\l

-- List tables
\dt

-- Describe table
\d employees

-- List users
\du

-- List all schemas
\dn

-- Show table size
\dt+

-- Execute file
\i /path/to/file.sql

-- Change database
\c database_name

-- Quit
\q
```

### Create Additional Users

```bash
podman exec -it employee-management-db psql -U postgres -d employee_management -c "
CREATE USER readonly WITH PASSWORD 'readonly_pass';
GRANT CONNECT ON DATABASE employee_management TO readonly;
GRANT USAGE ON SCHEMA public TO readonly;
GRANT SELECT ON ALL TABLES IN SCHEMA public TO readonly;
"
```

### Performance Monitoring

```sql
-- Active connections
SELECT * FROM pg_stat_activity;

-- Database size
SELECT pg_database.datname, pg_size_pretty(pg_database_size(pg_database.datname)) 
FROM pg_database;

-- Table sizes
SELECT schemaname, tablename, pg_size_pretty(pg_total_relation_size(schemaname||'.'||tablename))
FROM pg_tables WHERE schemaname = 'public';

-- Slow queries
SELECT pid, now() - pg_stat_activity.query_start AS duration, query 
FROM pg_stat_activity 
WHERE state = 'active' 
ORDER BY duration DESC;
```

---

## Cleanup & Reset

### Stop Services

```bash
# Stop containers (keeps volumes)
podman compose stop

# Stop and remove containers (keeps volumes)
podman compose down

# Stop, remove containers AND volumes
podman compose down -v
```

### Remove Specific Volume

```bash
# List volumes
podman volume ls

# Remove specific volume
podman volume rm postgres-data

# Remove all unused volumes
podman volume prune
```

### Complete Cleanup

```bash
# Nuclear option: remove everything
podman compose down -v
podman system prune -af --volumes

# Verify cleanup
podman ps -a
podman volume ls
podman images
```

### Reset to Fresh State

```bash
#!/bin/bash
# save as reset.sh

echo "Stopping services..."
podman compose down -v

echo "Removing volumes..."
podman volume rm postgres-data 2>/dev/null || true

echo "Starting fresh..."
podman compose up -d

echo "Waiting for database to be ready..."
sleep 5

echo "Checking logs..."
podman compose logs --tail=50
```

---

## Troubleshooting Commands

### Debug Checklist

```bash
# 1. Check if container is running
podman compose ps

# 2. Check logs for errors
podman compose logs postgres | grep -i error

# 3. Verify port is bound
podman port employee-management-db

# 4. Check healthcheck status
podman inspect employee-management-db --format='{{json .State.Health}}' | jq

# 5. Verify volumes are mounted
podman inspect employee-management-db --format='{{json .Mounts}}' | jq

# 6. Check SELinux denials (if issues persist)
sudo ausearch -m avc -ts recent

# 7. Test database connection
podman exec employee-management-db pg_isready -U postgres

# 8. Verify init scripts are present in container
podman exec employee-management-db ls -la /docker-entrypoint-initdb.d/
```

### Enable Verbose Logging

```yaml
services:
  postgres:
    environment:
      POSTGRES_INITDB_ARGS: "-E UTF8 --locale=C --verbose"
    command: ["postgres", "-c", "log_statement=all", "-c", "log_connections=on"]
```

---

## Security Best Practices

### 1. Use Environment Files

**Never commit passwords to git!**

```bash
# Create .env file
cat > .env << EOF
POSTGRES_PASSWORD=$(openssl rand -base64 32)
EOF

# Add to .gitignore
echo ".env" >> .gitignore
```

### 2. Use Secrets (Production)

```bash
# Create secret
echo "my_secure_password" | podman secret create postgres_password -

# Reference in compose.yml
services:
  postgres:
    secrets:
      - postgres_password
    environment:
      POSTGRES_PASSWORD_FILE: /run/secrets/postgres_password

secrets:
  postgres_password:
    external: true
```

### 3. Network Isolation

```yaml
services:
  postgres:
    networks:
      - backend
    # Don't expose port if only accessed internally
    # ports:
    #   - "5432:5432"

networks:
  backend:
    internal: true  # No external access
```

### 4. Read-only Root Filesystem

```yaml
services:
  postgres:
    read_only: true
    tmpfs:
      - /tmp
      - /var/run/postgresql
```

---

## Additional Resources

- [Podman Documentation](https://docs.podman.io/)
- [PostgreSQL Docker Hub](https://hub.docker.com/_/postgres)
- [Compose Specification](https://compose-spec.io/)
- [SELinux and Containers](https://access.redhat.com/documentation/en-us/red_hat_enterprise_linux/8/html/building_running_and_managing_containers/assembly_starting-with-containers_building-running-and-managing-containers#proc_relabeling-volume-content_assembly_mounting-storage)

---

## Next Steps

Once your Podman setup is working:

1. Review the [Kubernetes README](K8s-README.md) for production deployment
2. Set up monitoring and logging
3. Configure automated backups
4. Implement high availability with replicas

---

## License

This documentation is provided as-is for the Employee Management System project.