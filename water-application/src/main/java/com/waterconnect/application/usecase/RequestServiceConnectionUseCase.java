package com.waterconnect.application.usecase;

import com.waterconnect.domain.port.outbound.ConnectorRepository;
import com.waterconnect.domain.port.outbound.CustomerRepository;
import com.waterconnect.domain.port.outbound.DomainEventPublisher;
import com.waterconnect.domain.port.outbound.ServicePointRepository;

/**
 * Use case: Customer requests a new water service connection (Level 2).
 *
 * TODO: Implement in Level 2
 * - Validate customer exists
 * - Validate connector exists and is NOT a VALVE
 * - Check max connections for customer type (RESIDENTIAL=1, COMMERCIAL=5)
 * - Create ServicePoint via factory
 * - Persist and publish ServiceConnectionRequestedEvent
 */
public class RequestServiceConnectionUseCase {

    private final ServicePointRepository servicePointRepository;
    private final CustomerRepository customerRepository;
    private final ConnectorRepository connectorRepository;
    private final DomainEventPublisher eventPublisher;

    public RequestServiceConnectionUseCase(
            ServicePointRepository servicePointRepository,
            CustomerRepository customerRepository,
            ConnectorRepository connectorRepository,
            DomainEventPublisher eventPublisher) {
        this.servicePointRepository = servicePointRepository;
        this.customerRepository = customerRepository;
        this.connectorRepository = connectorRepository;
        this.eventPublisher = eventPublisher;
    }

    // TODO: Implement in Level 2
}
