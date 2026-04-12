package com.waterconnect.domain.model.aggregate;

import java.time.Instant;
import java.util.UUID;

import com.waterconnect.domain.model.entity.WaterMeter;
import com.waterconnect.domain.model.enums.ServicePointStatus;

/**
 * ServicePoint Aggregate Root (Level 2).
 *
 * TODO: Implement in Level 2
 * - Factory: ServicePoint.request(customerId, connectorId)
 * - Status lifecycle: REQUESTED → APPROVED → INSTALLED → ACTIVE → SUSPENDED/DISCONNECTED
 * - Method: approve() — only if REQUESTED
 * - Method: activate(WaterMeter meter) — only if APPROVED, sets meter + activatedAt
 * - Method: recordMeterReading(double readingM3) — must be >= previous, only if ACTIVE
 * - Method: suspend() / disconnect()
 * - Business rule: VALVE connectors cannot be used (validate in use case, not here)
 */
public class ServicePoint {

    private UUID servicePointId;
    private UUID customerId;
    private UUID connectorId;
    private WaterMeter meter;
    private ServicePointStatus status;
    private Instant requestedAt;
    private Instant activatedAt;

    protected ServicePoint() {}

    // TODO: Implement in Level 2
}
