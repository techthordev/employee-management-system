# 🚀 Employee Management System (EMS)

This is the final integration project of the learning journey, combining a **Spring Boot 4.0.2** backend and an **Angular 21** frontend into a production-grade system on **Fedora Linux**.

---

## 🏗️ Project Initialization

The following commands were used to bootstrap the core modules within this directory.

### Backend Setup (Spring Boot)

The backend was generated using the **Spring CLI**, targeting **Java 25** and the **Gradle Groovy DSL**.

```bash
spring init --boot-version=4.0.2 \
            --java-version=25 \
            --build=gradle \
            --type=gradle-project \
            --dependencies=data-jpa,security,postgresql,lombok,devtools,web \
            backend

```

**Post-Setup Task:**
Since `springdoc` is not a standard CLI identifier, add the following to `backend/build.gradle`:

```groovy
dependencies {
    implementation 'org.springdoc:springdoc-openapi-starter-webmvc-ui:2.8.4'
}

```

### Frontend Setup (Angular)

The frontend was initialized using the **Angular CLI**, with **Tailwind CSS** selected during the interactive prompt.

```bash
ng new frontend

```

* **Selected Styles:** SCSS
* **Routing:** Enabled
* **Features:** Tailwind CSS (Integrated via CLI prompts)

---

## ⚙️ Environment Configuration

The system is designed to connect to the infrastructure defined in `01_foundation`.

### Database Connection

Ensure the PostgreSQL 18 container is running in the foundation module:

```bash
cd ../../01_foundation/persistence
podman compose up -d

```

### Backend Configuration (`application.yml`)

The backend is configured to validate the schema against the provided SQL scripts:

* **URL:** `jdbc:postgresql://localhost:5432/${POSTGRES_DB}`
* **User:** `${DB_APP_USER}`
* **Hibernate:** `ddl-auto: validate`

---

## 🛠️ DevOps & Git Workflow

We leverage the **GitLab CLI (`glab`)** for project management.

* **Repository Status:** `glab repo view`
* **Version Control:** Managed via SSH (ED25519)

---

## 💡 Maintenance

To reset the business data without dropping the schema, use the provided reset script:

```bash
psql -h localhost -U postgres -d employee_management -f ../../01_foundation/persistence/scripts/reset.sql

```
