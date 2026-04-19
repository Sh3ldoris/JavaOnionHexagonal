package com.waterconnect.application.usecase;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.waterconnect.domain.event.DomainEvent;
import com.waterconnect.domain.exception.BusinessRuleViolationException;
import com.waterconnect.domain.exception.EntityNotFoundException;
import com.waterconnect.domain.model.aggregate.ServicePoint;
import com.waterconnect.domain.model.enums.ConnectorType;
import com.waterconnect.domain.port.outbound.ConnectorRepository;
import com.waterconnect.domain.port.outbound.CustomerRepository;
import com.waterconnect.domain.port.outbound.DomainEventPublisher;
import com.waterconnect.domain.port.outbound.ServicePointRepository;

@Service
public class RequestServicePointConnectionUseCase {

    private final ServicePointRepository servicePointRepository;
    private final CustomerRepository customerRepository;
    private final ConnectorRepository connectorRepository;
    private final DomainEventPublisher eventPublisher;

    public RequestServicePointConnectionUseCase(
            ServicePointRepository servicePointRepository,
            CustomerRepository customerRepository,
            ConnectorRepository connectorRepository,
            DomainEventPublisher eventPublisher
    ) {
        this.servicePointRepository = servicePointRepository;
        this.customerRepository = customerRepository;
        this.connectorRepository = connectorRepository;
        this.eventPublisher = eventPublisher;
    }

    @Transactional
    public UUID execute(UUID customerId, UUID connectorId) throws EntityNotFoundException, BusinessRuleViolationException {
        // Verify customer and connector exist
        var customer = customerRepository.findById(customerId);
        var connector = connectorRepository.findById(connectorId);

        if (customer.isEmpty() || connector.isEmpty()) {
            throw new EntityNotFoundException(
                    customer.isEmpty() ? "Customer" : "Connector",
                    customer.isEmpty() ? customerId : connectorId
            );
        }

        if (connector.get().getConnectorType() == ConnectorType.VALVE) {
            throw new BusinessRuleViolationException("Cannot create Service Point Connection for Connector of type: VALVE");
        }

        // Request a new Service Point
        var servicePoint = ServicePoint.request(customerId, connectorId);
        // Save the new Service Point
        this.servicePointRepository.save(servicePoint);

        // Publish Service Point requested events
        for (DomainEvent event : servicePoint.getConnectionRequestedEvents()) {
            this.eventPublisher.publish(event);
        }

        return servicePoint.getServicePointId();
    }
}
