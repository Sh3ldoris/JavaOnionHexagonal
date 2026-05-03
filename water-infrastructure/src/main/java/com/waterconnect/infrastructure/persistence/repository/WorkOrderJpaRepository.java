package com.waterconnect.infrastructure.persistence.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.waterconnect.infrastructure.persistence.entity.WorkOrderJpaEntity;

public interface WorkOrderJpaRepository extends JpaRepository<WorkOrderJpaEntity, UUID> {
    List<WorkOrderJpaEntity> findAllByServicePointId(UUID servicePointId);
}
