package com.waterconnect.infrastructure.persistence.adapter;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.waterconnect.domain.model.aggregate.WorkOrder;
import com.waterconnect.domain.port.outbound.WorkOrderRepository;
import com.waterconnect.infrastructure.persistence.entity.WorkOrderJpaEntity;
import com.waterconnect.infrastructure.persistence.repository.WorkOrderJpaRepository;

@Service
public class WorkOrderRepositoryAdapter implements WorkOrderRepository {

    private final WorkOrderJpaRepository repository;

    public WorkOrderRepositoryAdapter(WorkOrderJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public WorkOrder save(WorkOrder workOrder) {
        var entity = WorkOrderJpaEntity.fromDomain(workOrder);
        return this.repository
                .save(entity)
                .toDomain();
    }

    @Override
    public Optional<WorkOrder> findById(UUID id) {
        return this.repository
                .findById(id)
                .map(WorkOrderJpaEntity::toDomain);
    }

    @Override
    public List<WorkOrder> findByServicePointId(UUID servicePointId) {
        return this.repository
                .findAllByServicePointId(servicePointId).stream()
                .map(WorkOrderJpaEntity::toDomain)
                .toList();
    }
}
