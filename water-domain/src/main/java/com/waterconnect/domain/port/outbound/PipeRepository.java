package com.waterconnect.domain.port.outbound;

import java.util.Optional;
import java.util.UUID;

import com.waterconnect.domain.model.aggregate.Pipe;

/**
 * Outbound port for Pipe persistence.
 * Implemented by infrastructure adapters.
 */
public interface PipeRepository {
    Pipe save(Pipe pipe);
    Optional<Pipe> findById(UUID id);
}
