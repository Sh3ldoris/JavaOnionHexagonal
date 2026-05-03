package com.waterconnect.domain.event;

import java.time.Instant;
import java.util.UUID;

public record ServicePointApprovedEvent(
        UUID eventId,
        Instant occurredAt,
        UUID servicePointId
) implements DomainEvent {
    public ServicePointApprovedEvent(UUID servicePointId) {
        this(UUID.randomUUID(), Instant.now(), servicePointId);
    }
}
