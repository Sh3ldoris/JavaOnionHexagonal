package com.waterconnect.infrastructure.persistence.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.waterconnect.infrastructure.persistence.entity.CustomerJpaEntity;

/**
 * Spring Data JPA repository for CustomerJpaEntity.
 */
public interface CustomerJpaRepository extends JpaRepository<CustomerJpaEntity, UUID> {
}
