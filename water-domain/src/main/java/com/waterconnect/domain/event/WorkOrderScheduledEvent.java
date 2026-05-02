package com.waterconnect.domain.event;

import java.time.Instant;
import java.util.UUID;

public record WorkOrderScheduledEvent(
        UUID eventId,
        Instant occurredAt,
        UUID workOrderId
) {
    public WorkOrderScheduledEvent(UUID workOrderId) {
        this(UUID.randomUUID(), Instant.now(), workOrderId);
    }
}
