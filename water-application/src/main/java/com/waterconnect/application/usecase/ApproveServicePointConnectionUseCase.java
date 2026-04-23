package com.waterconnect.application.usecase;

import java.util.Objects;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.waterconnect.domain.exception.BusinessRuleViolationException;
import com.waterconnect.domain.exception.EntityNotFoundException;
import com.waterconnect.domain.model.aggregate.WorkOrder;
import com.waterconnect.domain.model.enums.WorkOrderType;
import com.waterconnect.domain.port.outbound.ServicePointRepository;
import com.waterconnect.domain.port.outbound.WorkOrderRepository;

@Service
public class ApproveServicePointConnectionUseCase {

    private final ServicePointRepository servicePointRepository;
    private final WorkOrderRepository workOrderRepository;

    public ApproveServicePointConnectionUseCase(
            ServicePointRepository servicePointRepository,
            WorkOrderRepository workOrderRepository
    ) {
        this.servicePointRepository = servicePointRepository;
        this.workOrderRepository = workOrderRepository;
    }

    @Transactional
    public void execute(UUID servicePointId) throws EntityNotFoundException, BusinessRuleViolationException {
        Objects.requireNonNull(servicePointId, "servicePointId must not be null");

        var servicePoint = servicePointRepository.findById(servicePointId).orElse(null);
        if (Objects.isNull(servicePoint)) {
            throw new EntityNotFoundException("ServicePoint",  servicePointId);
        }

        // Approve the service point
        servicePoint.approve();
        // Create a new work order of type: NEW_CONNECTION
        var newConnectionWo = WorkOrder.create(WorkOrderType.NEW_CONNECTION, servicePoint.getServicePointId());

        // TODO: Add create wo domain events

        // Save the new WO
        this.workOrderRepository.save(newConnectionWo);
    }
}
