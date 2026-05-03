package com.waterconnect.application.usecase;

import java.util.Objects;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.waterconnect.domain.event.DomainEvent;
import com.waterconnect.domain.exception.BusinessRuleViolationException;
import com.waterconnect.domain.exception.EntityNotFoundException;
import com.waterconnect.domain.model.entity.WaterMeter;
import com.waterconnect.domain.port.outbound.DomainEventPublisher;
import com.waterconnect.domain.port.outbound.ServicePointRepository;

@Service
public class ActivateServicePointConnectionUseCase {

    private final ServicePointRepository servicePointRepository;
    private final DomainEventPublisher domainEventPublisher;

    public ActivateServicePointConnectionUseCase(
            ServicePointRepository servicePointRepository,
            DomainEventPublisher domainEventPublisher
    ) {
        this.servicePointRepository = servicePointRepository;
        this.domainEventPublisher = domainEventPublisher;
    }

    @Transactional
    public void execute(UUID servicePointId) throws EntityNotFoundException, BusinessRuleViolationException {
        Objects.requireNonNull(servicePointId, "servicePointId must not be null");

        var servicePoint = servicePointRepository.findById(servicePointId).orElse(null);
        if (Objects.isNull(servicePoint)) {
            throw new EntityNotFoundException("ServicePoint",  servicePointId);
        }

        // Approve the service point
        // TODO: Create a new water meter based on inputs
        servicePoint.activate(WaterMeter.install("random_sn", 0));

        this.servicePointRepository.save(servicePoint);

        for (DomainEvent event : servicePoint.getEvents()) {
            this.domainEventPublisher.publish(event);
        }
        servicePoint.clearEvents();
    }
}
