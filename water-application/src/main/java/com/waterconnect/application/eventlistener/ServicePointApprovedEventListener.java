package com.waterconnect.application.eventlistener;

import java.util.Objects;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.waterconnect.domain.event.ServicePointApprovedEvent;
import com.waterconnect.domain.exception.BusinessRuleViolationException;
import com.waterconnect.domain.model.aggregate.WorkOrder;
import com.waterconnect.domain.model.enums.WorkOrderType;
import com.waterconnect.domain.port.outbound.WorkOrderRepository;

@Component
public class ServicePointApprovedEventListener {

    private final WorkOrderRepository workOrderRepository;

    public ServicePointApprovedEventListener(WorkOrderRepository workOrderRepository) {
        this.workOrderRepository = workOrderRepository;
    }

    @EventListener
    public void handleServicePointApprovedEvent(ServicePointApprovedEvent event) {
        if (Objects.isNull(event.servicePointId())) {
            throw new BusinessRuleViolationException("Service point id cannot be null");
        }

        // Create a new work order of type: NEW_CONNECTION
        var newConnectionWo = WorkOrder.create(WorkOrderType.NEW_CONNECTION, event.servicePointId());

        // Save the new WO
        this.workOrderRepository.save(newConnectionWo);
    }
}
