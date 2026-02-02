## 📄 database/README.md

```markdown
# Database Setup

PostgreSQL database configuration for Employee Management System.

---

## 📁 Structure

```bash
database/
├── init/
│   └── 01-init.sql       # User & database creation
├── schema/
│   └── 02-schema.sql     # Table definitions & indexes
├── data/
│   └── 03-data.sql       # Sample data (60 employees)
└── reset/
    └── reset.sql         # Reset script for development
```

---

## 🐘 Database Info

| Setting | Value |
|---------|-------|
| **Database** | `employee_management` |
| **Admin User** | `postgres` / `postgres` |
| **App User** | `springconnector` / `springconnector` |
| **Port** | `5432` (configurable via `.env`) |
| **Version** | PostgreSQL 18 |

---

## 🚀 Quick Start

### 1. Start Database

```bash
podman compose up -d
```

### 2. Check Status

```bash
podman compose ps
podman compose logs postgres
```

### 3. Connect to Database

```bash
# As postgres admin
podman exec -it employee-management-db psql -U postgres -d employee_management

# As application user
podman exec -it employee-management-db psql -U springconnector -d employee_management
```

---

## 🔧 Common Commands

### Container Management

```bash
# Start
podman compose up -d

# Stop
podman compose down

# Restart
podman compose restart

# View logs
podman compose logs -f postgres

# Check health
podman compose ps
```

### Database Operations

```bash
# Access PostgreSQL shell
podman exec -it employee-management-db psql -U postgres -d employee_management

# Run SQL file
podman exec -i employee-management-db psql -U postgres -d employee_management < ./reset/reset.sql

# Backup database
podman exec employee-management-db pg_dump -U postgres employee_management > backup.sql

# Restore database
podman exec -i employee-management-db psql -U postgres employee_management < backup.sql
```

---

## 🔄 Reset Database

### Soft Reset (keep structure, reload data)

```bash
podman exec -i employee-management-db psql -U postgres -d employee_management < ./reset/reset.sql
```

### Hard Reset (destroy everything)

```bash
podman compose down -v
podman compose up -d
```

---

## 📊 Sample Data

The database includes **60 sample employees** from different regions:
- 10 Hispanic/Latino
- 10 Brazilian
- 10 Spanish
- 10 Italian
- 10 English/American
- 10 Arabic

All with `@techthordev.com.br` email addresses.

---

## 🔐 Security Note

**Development credentials are hardcoded for convenience.**

For production:
- Use environment variables
- Strong passwords
- Restrict network access
- Enable SSL/TLS

---

## 🐛 Troubleshooting

### Port already in use

```bash
# Check what's using port 5432
sudo lsof -i :5432

# Change port in .env
POSTGRES_PORT=5435
```

### Container won't start

```bash
# Check logs
podman compose logs postgres

# Remove old volumes
podman compose down -v
podman volume prune
```

### Permission denied

```bash
# Check SELinux labels (Fedora)
ls -Z database/

# If needed, relabel
chcon -R -t container_file_t database/
```

### Can't connect from Spring Boot

```bash
# Verify container is running
podman ps

# Test connection
podman exec employee-management-db psql -U springconnector -d employee_management -c "SELECT 1;"

# Check application.properties matches database settings
```

---

## 📝 Notes

- Initialization scripts run **only on first start** (when volume is empty)
- To re-run init scripts: `podman compose down -v` then `up -d`
- Scripts execute in **alphabetical order** (01, 02, 03)
- Healthcheck ensures database is ready before Spring Boot connects
```
