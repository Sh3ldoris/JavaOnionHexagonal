package com.waterconnect.application.eventlistener;

import java.util.Objects;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.waterconnect.application.usecase.ActivateServicePointConnectionUseCase;
import com.waterconnect.domain.event.WorkOrderCompletedEvent;
import com.waterconnect.domain.exception.BusinessRuleViolationException;
import com.waterconnect.domain.exception.EntityNotFoundException;
import com.waterconnect.domain.port.outbound.WorkOrderRepository;

@Component
public class WorkOrderCompletedEventListener {

    private final ActivateServicePointConnectionUseCase activateServicePointConnectionUseCase;
    private final WorkOrderRepository workOrderRepository;

    public WorkOrderCompletedEventListener(
            ActivateServicePointConnectionUseCase activateServicePointConnectionUseCase,
            WorkOrderRepository workOrderRepository
    ) {
        this.activateServicePointConnectionUseCase = activateServicePointConnectionUseCase;
        this.workOrderRepository = workOrderRepository;
    }

    @EventListener
    public void onUserCreated(WorkOrderCompletedEvent event) {
        // Get wo
        var workOrder = this.workOrderRepository
                .findById(event.workOrderId())
                .orElse(null);

        if (Objects.isNull(workOrder)) {
            throw new EntityNotFoundException("WorkOrder",  event.workOrderId());
        }

        if (Objects.isNull(workOrder.getServicePointId())) {
            throw new BusinessRuleViolationException("ServicePoint ID is not assigned to WorkOrder: " + workOrder.getWorkOrderId());
        }

        this.activateServicePointConnectionUseCase.execute(workOrder.getServicePointId());
    }
}
