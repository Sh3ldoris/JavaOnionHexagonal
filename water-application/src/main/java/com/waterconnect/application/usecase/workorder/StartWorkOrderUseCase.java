package com.waterconnect.application.usecase.workorder;

import java.util.Objects;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.waterconnect.domain.event.DomainEvent;
import com.waterconnect.domain.exception.BusinessRuleViolationException;
import com.waterconnect.domain.exception.EntityNotFoundException;
import com.waterconnect.domain.port.outbound.DomainEventPublisher;
import com.waterconnect.domain.port.outbound.WorkOrderRepository;

@Service
public class StartWorkOrderUseCase {

    private final WorkOrderRepository workOrderRepository;
    private final DomainEventPublisher domainEventPublisher;

    public StartWorkOrderUseCase(
            WorkOrderRepository workOrderRepository,
            DomainEventPublisher domainEventPublisher
    ) {
        this.workOrderRepository = workOrderRepository;
        this.domainEventPublisher = domainEventPublisher;
    }

    @Transactional
    public void execute(UUID workOrderId) throws BusinessRuleViolationException, EntityNotFoundException {
        Objects.requireNonNull(workOrderId);
        // Get WO
        var wo = this.workOrderRepository.findById(workOrderId).orElse(null);
        if (Objects.isNull(wo)) {
            throw new EntityNotFoundException("WorkOrder", workOrderId);
        }

        wo.start();

        this.workOrderRepository.save(wo);

        for (DomainEvent event: wo.getEvents()) {
            domainEventPublisher.publish(event);
        }
        wo.cleanEvents();
    }
}
