package com.waterconnect.domain.outbound;

import java.util.Optional;
import java.util.UUID;

import com.waterconnect.domain.model.aggregate.Connector;

/**
 * Outbound port for Connector persistence.
 * Implemented by infrastructure adapters.
 */
public interface ConnectorRepository {
    Connector save(Connector connector);
    Optional<Connector> findById(UUID id);
}
