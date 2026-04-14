package com.waterconnect.infrastructure.persistence.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.waterconnect.infrastructure.persistence.entity.PipeJpaEntity;

/**
 * Spring Data JPA repository for PipeJpaEntity.
 */
public interface PipeJpaRepository extends JpaRepository<PipeJpaEntity, UUID> {
}
