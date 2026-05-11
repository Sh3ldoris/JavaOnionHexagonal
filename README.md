# 🚰 Water Connection System

A production-style backend built to demonstrate **Domain-Driven Design**, **Hexagonal Architecture**, and **event-driven workflows**.

The domain models a water utility company — tracking physical network infrastructure, customer connections, metering, and field work orders — with strict business rules enforced at the domain level.

---

## Domain
The detailed description can be found in the [domain docs here](docs/1_DOMAIN.md)

---

## Architecture

More on the actual architecture and key design decisions can be found [in the docs](docs/2_ARCHITECTURE.md)

---

## Tech Stack

| Area        | Actual stuff used                        |
|-------------|------------------------------------------|
| Language    | Java 25                                  |
| Framework   | Spring Boot 4.x                          |
| Persistence | JPA / Hibernate · PostgreSQL · Liquibase |
| API         | OpenAPI 3.1                              |
| Testing     | JUnit 5 + Mockito + Instancio            |

---

## Running Locally

**Prerequisites:** Java 25, Maven, Docker

1. **Start Docker** — the app depends on Docker for local infrastructure (PostgreSQL)
2. **Compile the project**
   ```bash
   mvn clean compile
   ```

3. **Run the application** — start the Spring Boot app in the `api` module with the `dev` profile
- Swagger UI → `http://localhost:8080/swagger-ui.html`
---

## Status

| Level | Scope | Status |
|---|---|---|
| 1 — Foundation | Domain model, CRUD, JPA adapters | ✅ Complete |
| 2 — Service Connections | ServicePoint, business rules, domain events | ✅ Complete |
| 3 — Work Orders | State machine, event-driven activation | ✅ Complete |
| 4 — Advanced | Read models, specification pattern, idempotency, audit trail | 🔧 In progress |
