package com.waterconnect.domain.outbound;

import java.util.Optional;
import java.util.UUID;

import com.waterconnect.domain.model.aggregate.WorkOrder;

/**
 * Outbound port for WorkOrder persistence.
 * Implemented by infrastructure adapters.
 */
public interface WorkOrderRepository {
    WorkOrder save(WorkOrder workOrder);
    Optional<WorkOrder> findById(UUID id);
    java.util.List<WorkOrder> findByServicePointId(UUID servicePointId);
}
