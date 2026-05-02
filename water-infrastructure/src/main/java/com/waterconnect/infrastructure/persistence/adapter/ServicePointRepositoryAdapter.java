package com.waterconnect.infrastructure.persistence.adapter;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.waterconnect.domain.model.aggregate.ServicePoint;
import com.waterconnect.domain.port.outbound.ServicePointRepository;
import com.waterconnect.infrastructure.persistence.entity.ServicePointJpaEntity;
import com.waterconnect.infrastructure.persistence.repository.ServicePointJpaRepository;

@Repository
public class ServicePointRepositoryAdapter implements ServicePointRepository {

    private final ServicePointJpaRepository servicePointJpaRepository;

    public ServicePointRepositoryAdapter(ServicePointJpaRepository servicePointJpaRepository) {
        this.servicePointJpaRepository = servicePointJpaRepository;
    }

    @Override
    public ServicePoint save(ServicePoint servicePoint) {
        return this.servicePointJpaRepository.save(
                ServicePointJpaEntity.fromDomain(servicePoint)
        ).toDomain();
    }

    @Override
    public Optional<ServicePoint> findById(UUID id) {
        return this.servicePointJpaRepository
                .findById(id)
                .map(ServicePointJpaEntity::toDomain);
    }

    @Override
    public List<ServicePoint> findByCustomerId(UUID customerId) {
        return this.servicePointJpaRepository
                .findAllByCustomerId(customerId).stream()
                .map(ServicePointJpaEntity::toDomain)
                .toList();
    }
}
