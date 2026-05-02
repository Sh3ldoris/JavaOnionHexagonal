package com.waterconnect.domain.event;

import java.time.Instant;
import java.util.UUID;

public record WorkOrderCompletedEvent(
        UUID eventId,
        Instant occurredAt,
        UUID workOrderId
) implements DomainEvent {
    public WorkOrderCompletedEvent(UUID workOrderId) {
        this(UUID.randomUUID(), Instant.now(), workOrderId);
    }
}
