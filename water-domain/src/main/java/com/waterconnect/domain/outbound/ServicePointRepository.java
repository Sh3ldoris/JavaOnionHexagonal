package com.waterconnect.domain.outbound;

import java.util.Optional;
import java.util.UUID;

import com.waterconnect.domain.model.aggregate.ServicePoint;

/**
 * Outbound port for ServicePoint persistence.
 * Implemented by infrastructure adapters.
 */
public interface ServicePointRepository {
    ServicePoint save(ServicePoint servicePoint);
    Optional<ServicePoint> findById(UUID id);
    java.util.List<ServicePoint> findByCustomerId(UUID customerId);
}
