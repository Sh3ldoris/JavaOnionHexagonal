package com.waterconnect.domain.event;

import java.time.Instant;
import java.util.UUID;

public record CustomerRegisteredEvent(
    UUID eventId,
    Instant occurredAt,
    UUID customerId,
    String customerName
) implements DomainEvent {
    public CustomerRegisteredEvent(UUID customerId, String customerName) {
        this(UUID.randomUUID(), Instant.now(), customerId, customerName);
    }
}
