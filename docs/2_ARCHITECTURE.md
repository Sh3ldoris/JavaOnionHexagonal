## Architecture

The project follows **Hexagonal (Ports & Adapters)** architecture with a strict dependency rule: inner layers know nothing about outer ones.

```
┌──────────────────────────────────────────────┐
│              API Layer                        │  REST controllers, OpenAPI DTOs
├──────────────────────────────────────────────┤
│           Application Layer                   │  Use cases, Queries, Event listener
│                                               │  @Service, @Transactional
├──────────────────────────────────────────────┤
│          Domain Layer  ★ core                 │  Zero framework dependencies
│  Aggregates · Value Objects · Domain Events   │
│  Ports (interfaces) · Business Rules          │
├──────────────────────────────────────────────┤
│         Infrastructure Layer                  │  JPA adapters, event publishing
└──────────────────────────────────────────────┘
```

**The domain layer has zero Spring dependencies.** Ports are interfaces defined in the domain; adapters that implement them live in infrastructure. This means the entire business logic is testable with plain JUnit — no application context needed.

---

## Key Design Decisions

**Records for Value Objects, classes for Entities**. No Lombok used anywhere.

**Aggregates enforce invariants** — state changes only happen through aggregate methods, never via direct field mutation.

**Domain events over direct coupling** — when a work order completes, it emits `WorkOrderCompletedEvent`. The application layer listens and triggers service point activation. The work order aggregate knows nothing about service points.

**Spec-first API** — the OpenAPI spec (`openapi.yaml`) is the source of truth for the API contract. Domain objects are never exposed directly.

---
