package com.waterconnect.infrastructure.persistence.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.waterconnect.infrastructure.persistence.entity.ServicePointJpaEntity;

public interface ServicePointJpaRepository extends JpaRepository<ServicePointJpaEntity, UUID> {
    java.util.List<ServicePointJpaEntity> findAllByCustomerId(UUID customerId);
}
