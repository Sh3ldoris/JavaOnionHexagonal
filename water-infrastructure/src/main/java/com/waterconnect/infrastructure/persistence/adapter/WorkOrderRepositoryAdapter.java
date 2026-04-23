package com.waterconnect.infrastructure.persistence.adapter;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.waterconnect.domain.model.aggregate.WorkOrder;
import com.waterconnect.domain.port.outbound.WorkOrderRepository;

@Service
public class WorkOrderRepositoryAdapter implements WorkOrderRepository {
    @Override
    public WorkOrder save(WorkOrder workOrder) {
        return null;
    }

    @Override
    public Optional<WorkOrder> findById(UUID id) {
        return Optional.empty();
    }

    @Override
    public List<WorkOrder> findByServicePointId(UUID servicePointId) {
        return List.of();
    }
}
