package com.waterconnect.application.usecase.workorder;

import java.util.Objects;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.waterconnect.application.command.ScheduleWorkOrderCommand;
import com.waterconnect.domain.event.DomainEvent;
import com.waterconnect.domain.exception.BusinessRuleViolationException;
import com.waterconnect.domain.exception.EntityNotFoundException;
import com.waterconnect.domain.port.outbound.DomainEventPublisher;
import com.waterconnect.domain.port.outbound.WorkOrderRepository;

@Service
public class ScheduleWorkOrderUseCase {

    private final WorkOrderRepository workOrderRepository;
    private final DomainEventPublisher domainEventPublisher;

    public ScheduleWorkOrderUseCase(
            WorkOrderRepository workOrderRepository,
            DomainEventPublisher domainEventPublisher
    ) {
        this.workOrderRepository = workOrderRepository;
        this.domainEventPublisher = domainEventPublisher;
    }

    @Transactional
    public void execute(ScheduleWorkOrderCommand command) throws BusinessRuleViolationException, EntityNotFoundException {
        Objects.requireNonNull(command.workOrderId());
        // Get WO
        var wo = this.workOrderRepository.findById(command.workOrderId()).orElse(null);
        if (Objects.isNull(wo)) {
            throw new EntityNotFoundException("WorkOrder", command.workOrderId());
        }

        wo.schedule(command.date(), command.team());

        this.workOrderRepository.save(wo);

        for (DomainEvent event: wo.getEvents()) {
            domainEventPublisher.publish(event);
        }
        wo.cleanEvents();
    }
}
