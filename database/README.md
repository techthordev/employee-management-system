# Database Setup

PostgreSQL database configuration for Employee Management System.

---

## 📁 Structure
```bash
database/
├── init/
│   └── 01-init.sql                    # User & database creation
├── schema/
│   ├── 02-schema.sql                  # Business tables (departments, employee)
│   ├── 03-auth-schema.sql             # Authentication (auth.users, auth.authorities)
│   └── 04-link-employee-auth.sql      # Foreign key between employee & auth
├── data/
│   ├── 05-departments-data.sql        # 8 departments (HR, IT, Finance, etc.)
│   ├── 06-employees-data.sql          # 60 sample employees
│   └── 07-link-sample-users.sql       # Link HR users to employee records
└── reset/
    └── reset.sql                       # Reset script for development
```

---

## 🐘 Database Info

| Setting | Value |
|---------|-------|
| **Database** | `employee_management` |
| **Admin User** | `postgres` / `postgres` |
| **App User** | `springconnector` / `springconnector` |
| **Port** | `5432` (configurable via `.env`) |
| **Version** | PostgreSQL 18 Alpine |

---

## 🏗️ Schema Architecture

### **Two-Schema Design:**

**`auth` Schema** (Authentication & Authorization)
- `auth.users` - User accounts for login
- `auth.authorities` - Role assignments (RBAC)
- Owned by: `postgres`
- Access: `springconnector` has SELECT only

**`public` Schema** (Business Data)
- `departments` - Company departments
- `employee` - Employee records with department assignment
- Owned by: `springconnector`
- Access: Full CRUD for `springconnector`

---

## 👥 Default Users

| Username | Password | Role | Description |
|----------|----------|------|-------------|
| `admin` | `password` | `ROLE_ADMIN` | System Administrator |
| `hr.admin` | `password` | `ROLE_HR_ADMIN` | HR Department Chief (Full CRUD) |
| `hr.manager` | `password` | `ROLE_HR_MANAGER` | Senior HR (CRUD except Delete) |
| `hr.employee` | `password` | `ROLE_HR_EMPLOYEE` | Junior HR (Read-Only) |

**Note:** Password is BCrypt hash of `"password"` - **change in production!**

---

## 📊 Sample Data

### **Departments** (8 total)
- Human Resources (HR)
- Information Technology (IT)
- Finance (FIN)
- Sales (SAL)
- Marketing (MKT)
- Operations (OPS)
- Customer Support (SUP)
- Research & Development (RND)

### **Employees** (60 total)
Distributed across all departments from different regions:
- 10 Hispanic/Latino
- 10 Brazilian
- 10 Spanish
- 10 Italian
- 10 English/American
- 10 Arabic

All with `@techthordev.com.br` email addresses.

### **Linked HR Users** (3 total)
- Lucas Martinez (`hr.admin`) - HR Department
- Bruno Pereira (`hr.manager`) - HR Department
- Juan Lopez (`hr.employee`) - HR Department

---

## 🚀 Quick Start

### 1. Start Database
```bash
podman compose up -d
```

### 2. Check Status
```bash
podman compose ps
podman compose logs -f postgres
```

**Expected output:**
```
✅ CREATE DATABASE
✅ CREATE SCHEMA (auth)
✅ INSERT 0 8 (departments)
✅ INSERT 0 60 (employees)
✅ UPDATE 3 (linked HR users)
✅ database system is ready to accept connections
```

### 3. Verify Installation
```bash
# Check schemas
podman exec -it employee-management-db psql -U postgres -d employee_management -c "\dn"

# Check departments
podman exec -it employee-management-db psql -U postgres -d employee_management \
  -c "SELECT * FROM departments;"

# Check auth users
podman exec -it employee-management-db psql -U postgres -d employee_management -c "
SELECT u.username, STRING_AGG(a.authority, ', ') AS roles
FROM auth.users u
JOIN auth.authorities a ON u.username = a.username
GROUP BY u.username
ORDER BY u.username;"

# Check employee count
podman exec -it employee-management-db psql -U postgres -d employee_management \
  -c "SELECT COUNT(*) FROM employee;"
```

### 4. Connect to Database
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

# Check table sizes
podman exec -it employee-management-db psql -U postgres -d employee_management -c "
SELECT schemaname, tablename, pg_size_pretty(pg_total_relation_size(schemaname||'.'||tablename)) AS size
FROM pg_tables
WHERE schemaname IN ('public', 'auth')
ORDER BY pg_total_relation_size(schemaname||'.'||tablename) DESC;"
```

---

## 🔄 Reset Database

### Soft Reset (keep structure, reload data)
```bash
podman exec -i employee-management-db psql -U postgres -d employee_management \
  < database/reset/reset.sql
```

### Hard Reset (destroy everything)
```bash
podman compose down -v
podman compose up -d
```

**Note:** Hard reset will re-run all initialization scripts.

---

## 🔐 Security Configuration

### **Development (Current)**
- Hardcoded credentials for convenience
- HTTP Basic Authentication
- Stateless sessions
- BCrypt password hashing

### **Production Checklist**
- [ ] Use environment variables for passwords
- [ ] Generate strong, unique passwords
- [ ] Enable SSL/TLS for PostgreSQL
- [ ] Restrict network access (firewall rules)
- [ ] Use connection pooling
- [ ] Enable audit logging
- [ ] Implement JWT authentication (replace HTTP Basic)
- [ ] Regular security updates

---

## 🐛 Troubleshooting

### Port already in use
```bash
# Check what's using port 5432
sudo lsof -i :5432

# Kill the process
sudo kill -9 

# Or change port in compose.yml
POSTGRES_PORT=5433
```

### Container won't start
```bash
# Check logs for errors
podman compose logs postgres

# Remove old volumes and retry
podman compose down -v
podman volume prune -f
podman compose up -d
```

### Permission denied (SELinux - Fedora/RHEL)
```bash
# Check SELinux labels
ls -Z database/

# Relabel if needed
chcon -R -t container_file_t database/

# Or disable SELinux for testing (not recommended for production)
sudo setenforce 0
```

### Can't connect from Spring Boot
```bash
# 1. Verify container is running
podman ps

# 2. Test database connection
podman exec employee-management-db psql -U springconnector -d employee_management -c "SELECT 1;"

# 3. Check application.properties
cat src/main/resources/application.properties | grep datasource

# 4. Check network
podman inspect employee-management-db | grep IPAddress

# 5. Test from host
psql -h localhost -p 5432 -U springconnector -d employee_management
```

### Scripts not executing
```bash
# Check if volume is empty (scripts only run on first start)
podman volume inspect employee-management-system_postgres_data

# Force re-initialization
podman compose down -v
podman compose up -d

# Check script syntax
for file in database/**/*.sql; do
  echo "Checking $file..."
  psql -U postgres -d employee_management -f "$file" --dry-run 2>&1 | head -5
done
```

### Wrong employee count
```bash
# Check actual count
podman exec -it employee-management-db psql -U postgres -d employee_management -c "
SELECT 
  d.name, 
  COUNT(e.id) AS count 
FROM departments d 
LEFT JOIN employee e ON d.id = e.department_id 
GROUP BY d.name 
ORDER BY d.name;"

# Should total 60 employees
```

---

## 📊 Useful Queries

### Check database size
```sql
SELECT pg_size_pretty(pg_database_size('employee_management'));
```

### List all employees with department
```sql
SELECT 
  e.id,
  e.first_name,
  e.last_name,
  e.email,
  d.name AS department,
  e.created_at
FROM employee e
JOIN departments d ON e.department_id = d.id
ORDER BY d.name, e.last_name;
```

### Find HR staff
```sql
SELECT 
  e.first_name,
  e.last_name,
  e.user_id,
  a.authority AS role
FROM employee e
JOIN auth.authorities a ON e.user_id = a.username
WHERE e.user_id IS NOT NULL
ORDER BY e.id;
```

### Employee distribution
```sql
SELECT 
  d.name AS department,
  COUNT(e.id) AS employee_count,
  ROUND(COUNT(e.id) * 100.0 / SUM(COUNT(e.id)) OVER (), 1) AS percentage
FROM departments d
LEFT JOIN employee e ON d.id = e.department_id
GROUP BY d.name
ORDER BY employee_count DESC;
```

---

## 📝 Notes

- Initialization scripts run **only on first start** (when volume is empty)
- To re-run init scripts: `podman compose down -v` then `up -d`
- Scripts execute in **alphabetical order** (01, 02, 03, ...)
- Healthcheck ensures database is ready before Spring Boot connects
- All timestamps are in UTC
- Employee IDs start at 1 and auto-increment
- Department IDs are stable (1-8)

---

## 🔗 Related Documentation

- [PostgreSQL 18 Documentation](https://www.postgresql.org/docs/18/)
- [Spring Security JDBC Authentication](https://docs.spring.io/spring-security/reference/servlet/authentication/passwords/jdbc.html)
- [Podman Compose](https://github.com/containers/podman-compose)

---

**Last Updated:** 2026-02-07  
**PostgreSQL Version:** 18.1 Alpine  
**Schema Version:** 1.0 (Phase 1)