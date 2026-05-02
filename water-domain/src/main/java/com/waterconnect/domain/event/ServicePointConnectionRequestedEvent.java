package com.waterconnect.domain.event;

import java.time.Instant;
import java.util.UUID;

public record ServicePointConnectionRequestedEvent(
    UUID eventId,
    Instant occurredAt,
    UUID servicePointId
) implements DomainEvent {
    public ServicePointConnectionRequestedEvent(UUID servicePointId) {
        this(UUID.randomUUID(), Instant.now(), servicePointId);
    }
}
