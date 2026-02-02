# Employee Management System

> Full-stack employee management application with Spring Boot, Angular, JWT authentication, and comprehensive API documentation

[![Java](https://img.shields.io/badge/Java-25+-orange?logo=openjdk)](https://openjdk.org/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.0-brightgreen?logo=spring)](https://spring.io/projects/spring-boot)
[![Angular](https://img.shields.io/badge/Angular-21+-red?logo=angular)](https://angular.io/)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-18+-blue?logo=postgresql)](https://www.postgresql.org/)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

---

## 🎯 What This Is

A production-ready employee management system demonstrating:

- **RESTful API design** with Spring Boot 4
- **Interactive API documentation** with Swagger/OpenAPI
- **Modern frontend** with Angular
- **JWT authentication** and role-based access control
- **Database management** with PostgreSQL and JPA
- **Containerized development** with Podman/Docker
- **Best practices** for enterprise Java applications

**Built as a portfolio project** to showcase full-stack development skills for backend developer positions.

---

## 🏗️ Architecture
```
┌──────────────┐         ┌─────────────────┐         ┌──────────────┐
│   Angular    │ ◄─────► │  Spring Boot    │ ◄─────► │  PostgreSQL  │
│   Frontend   │   HTTP  │   REST API      │   JPA   │   Database   │
└──────────────┘         └─────────────────┘         └──────────────┘
```

---

## 🛠️ Tech Stack

**Backend:**
- Spring Boot 4.0.2
- Spring Data JPA
- PostgreSQL 15+
- Swagger/OpenAPI 3.0
- JWT Authentication
- Maven

**Frontend:**
- Angular 17+
- TypeScript
- Angular Material (planned)
- RxJS

**DevOps:**
- Podman/Docker
- GitHub Actions (planned)
- Cloud deployment (planned)

---

## 📂 Project Structure
```
employee-management-system/
├── backend/              # Spring Boot REST API
│   ├── src/
│   └── pom.xml
├── frontend/             # Angular SPA
│   ├── src/
│   └── package.json
└── README.md
```

---

## 🚀 Current Status

| Component | Status |
|-----------|--------|
| Project Setup | ✅ Complete |
| Backend Structure | 🚧 In Progress |
| REST API | 📅 Next |
| Swagger Docs | 📅 Planned |
| JWT Security | 📅 Planned |
| Frontend | 📅 Planned |

---

## 📋 Roadmap

### Phase 1: REST API Foundation (Current)
- [ ] Database configuration
- [ ] Employee entity & repository
- [ ] Service layer
- [ ] REST controllers
- [ ] Exception handling
- [ ] Swagger integration

### Phase 2: Security
- [ ] JWT implementation
- [ ] User authentication
- [ ] Role-based access control
- [ ] Swagger authentication

### Phase 3: Frontend
- [ ] Angular components
- [ ] Employee CRUD views
- [ ] JWT interceptor
- [ ] Routing & guards

### Phase 4: Deployment
- [ ] Docker containerization
- [ ] CI/CD pipeline
- [ ] Cloud deployment

---

## 🔧 Development Setup

Detailed setup instructions in each subdirectory:
- [Backend Setup](./backend/README.md)
- [Frontend Setup](./frontend/README.md)

---

## 📚 Documentation

- API Documentation: Available via Swagger UI (coming soon)
- Architecture: See `/docs` (coming soon)

---

## 👨‍💻 Author

**Thorsten Fey**  
IT Support → Backend Developer Transition

- 🌍 [techthordev.com.br](https://techthordev.com.br)
- 💼 [LinkedIn](https://linkedin.com/in/thorstenfey)
- 💻 [GitHub](https://github.com/techthordev)

---

## 📝 License

MIT License - see [LICENSE](LICENSE) file

---

⭐ **Star this repo if you find it helpful!**
