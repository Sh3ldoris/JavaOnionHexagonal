package com.waterconnect.domain.event;

import java.time.Instant;
import java.util.UUID;

public record ServiceConnectionActivatedEvent(
        UUID eventId,
        Instant occurredAt,
        UUID servicePointId
) implements DomainEvent {
    public ServiceConnectionActivatedEvent(UUID servicePointId) {
        this(UUID.randomUUID(), Instant.now(), servicePointId);
    }
}
