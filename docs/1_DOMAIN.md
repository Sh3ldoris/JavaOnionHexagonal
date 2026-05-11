## Domain

Four bounded contexts, each with its own aggregate root:

```
 Customer ──requests──► ServicePoint ──triggers──► WorkOrder
                             │                         │
                             └──taps into──► Connector (Network)
```

A **Customer** requests a **ServicePoint** (water connection). The system approves it, auto-creates a **WorkOrder**, and dispatches a field team. When the work order completes, the service point activates and meter readings can begin.

### Core Business Rules

- Residential customers: max **1 active connection**. Commercial: up to **5**.
- A **VALVE** connector cannot serve as a service point tap.
- Meter readings must be **monotonically increasing** — water doesn't flow backwards.
- Pipes can only be joined by connectors with **compatible diameters** (±20% tolerance).
- A service point can only activate **after** its work order reaches `COMPLETED`.

### Work Order Lifecycle

```
CREATED ──► SCHEDULED ──► IN_PROGRESS ──► COMPLETED
   │
   └──► CANCELLED  (only before IN_PROGRESS)
```

Completing a `NEW_CONNECTION` work order automatically fires `ServiceConnectionActivatedEvent`, which triggers service point activation — no manual step needed.
