# Frontend — Employee Management System

This frontend is a **production-oriented Angular application** built to consume the
Employee Management REST API defined via **OpenAPI / Swagger**.

The focus is on **clarity, correctness, and real-world architecture** rather than
framework magic or demo shortcuts.

---

## 🎯 Purpose

This frontend demonstrates how a **modern Angular 21+ application** is built in
practice when consuming a **well-defined REST API**.

Key goals:

- Swagger-driven API consumption
- Signals-first state management
- Clear separation between UI, state, and API access
- Enterprise-friendly structure
- Angular Material-based UI (tables, pagination, sorting)

---

## 🧠 Architectural Principles

### Swagger-first Development

The backend API is treated as the **single source of truth**.

All frontend features are derived directly from the OpenAPI specification:

- available endpoints
- request parameters (pagination, sorting)
- response models
- error structures

The frontend **does not invent APIs** — it implements what Swagger defines.

---

### Signals-first State Management

This application uses **Angular Signals** as the primary state mechanism.

Signals are used for:

- component state
- pagination state
- sorting state
- loading & error states

Derived state is handled via **computed signals** and **effects**.

RxJS is intentionally limited to:

- HTTP communication
- truly asynchronous streams

There is **no global state library** unless strictly required.

---

## 🖥️ UI & UX

The UI is built using **Angular Material**.

### Core UI Components

- `MatTable` — employee list
- `MatPaginator` — backend-driven pagination
- `MatSort` — backend-driven sorting
- `MatFormField` & `MatInput` — search input
- `MatProgressSpinner` — loading state
- `MatSnackBar` — error feedback

The UI is designed to directly reflect backend capabilities
(pagination, sorting, error handling).

---

## 🔌 Backend Integration

### API Base URL

During development, the frontend expects the backend to run at:

