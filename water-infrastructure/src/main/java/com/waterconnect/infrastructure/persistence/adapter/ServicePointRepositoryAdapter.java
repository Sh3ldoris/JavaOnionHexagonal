package com.waterconnect.infrastructure.persistence.adapter;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.waterconnect.domain.model.aggregate.ServicePoint;
import com.waterconnect.domain.port.outbound.ServicePointRepository;

@Repository
public class ServicePointRepositoryAdapter implements ServicePointRepository {

    private final ServicePointRepository jpaRepository;

    public ServicePointRepositoryAdapter(ServicePointRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public ServicePoint save(ServicePoint servicePoint) {
        return null;
    }

    @Override
    public Optional<ServicePoint> findById(UUID id) {
        return Optional.empty();
    }

    @Override
    public List<ServicePoint> findByCustomerId(UUID customerId) {
        return List.of();
    }
}
