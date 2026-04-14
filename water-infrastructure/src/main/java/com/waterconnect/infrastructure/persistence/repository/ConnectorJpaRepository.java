package com.waterconnect.infrastructure.persistence.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.waterconnect.infrastructure.persistence.entity.ConnectorJpaEntity;

/**
 * Spring Data JPA repository for ConnectorJpaEntity.
 */
public interface ConnectorJpaRepository extends JpaRepository<ConnectorJpaEntity, UUID> {
}
