package com.waterconnect.domain.outbound;

import com.waterconnect.domain.event.DomainEvent;

/**
 * Outbound port for publishing domain events.
 * Infrastructure layer provides the adapter (e.g., Spring ApplicationEventPublisher).
 */
public interface DomainEventPublisher {
    void publish(DomainEvent event);
}
