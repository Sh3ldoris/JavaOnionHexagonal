package com.waterconnect.domain.model.aggregate;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import com.waterconnect.domain.event.ServicePointConnectionRequestedEvent;
import com.waterconnect.domain.exception.BusinessRuleViolationException;
import com.waterconnect.domain.model.entity.WaterMeter;
import com.waterconnect.domain.model.enums.ServicePointStatus;

/**
 * ServicePoint Aggregate Root (Level 2).
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

    private final List<ServicePointConnectionRequestedEvent> connectionRequestedEvents = new ArrayList<>();

    protected ServicePoint() {}

    /**
     * Request a new service point for provided connector and customer
     */
    public static ServicePoint request(UUID customerId, UUID connectorId) {
        // Validate parameters
        Objects.requireNonNull(customerId, "Customer id must not be null");
        Objects.requireNonNull(connectorId, "Connector id must not be null");

        // Create a new domain object
        var servicePoint = new ServicePoint();
        // Set attributes from parameters
        servicePoint.setCustomerId(customerId);
        servicePoint.setConnectorId(connectorId);
        // Set other attributes
        var servicePointId = UUID.randomUUID();
        servicePoint.setServicePointId(servicePointId);
        servicePoint.setStatus(ServicePointStatus.REQUESTED);
        servicePoint.setRequestedAt(Instant.now());

        // Create a requested domain event
        servicePoint.connectionRequestedEvents.add(new ServicePointConnectionRequestedEvent(servicePointId));

        return servicePoint;
    }

    /**
     * Set status to APPROVED
     * @throws BusinessRuleViolationException only if the status before is not REQUESTED
     */
    public void approve() throws BusinessRuleViolationException {
        if (this.status != ServicePointStatus.REQUESTED) {
            throw new BusinessRuleViolationException("Only REQUESTED service point can be approved, current: " + this.status);
        }

        this.status = ServicePointStatus.APPROVED;
    }

    public List<ServicePointConnectionRequestedEvent> getConnectionRequestedEvents() {
        return Collections.unmodifiableList(this.connectionRequestedEvents);
    }

    public UUID getServicePointId() {
        return servicePointId;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public UUID getConnectorId() {
        return connectorId;
    }

    public WaterMeter getMeter() {
        return meter;
    }

    public ServicePointStatus getStatus() {
        return status;
    }

    public Instant getRequestedAt() {
        return requestedAt;
    }

    public Instant getActivatedAt() {
        return activatedAt;
    }

    protected void setServicePointId(UUID servicePointId) {
        this.servicePointId = servicePointId;
    }

    protected void setCustomerId(UUID customerId) {
        this.customerId = customerId;
    }

    protected void setConnectorId(UUID connectorId) {
        this.connectorId = connectorId;
    }

    protected void setMeter(WaterMeter meter) {
        this.meter = meter;
    }

    protected void setStatus(ServicePointStatus status) {
        this.status = status;
    }

    protected void setRequestedAt(Instant requestedAt) {
        this.requestedAt = requestedAt;
    }

    protected void setActivatedAt(Instant activatedAt) {
        this.activatedAt = activatedAt;
    }

    private boolean isActive() {
        return this.status == ServicePointStatus.ACTIVE;
    }
}
