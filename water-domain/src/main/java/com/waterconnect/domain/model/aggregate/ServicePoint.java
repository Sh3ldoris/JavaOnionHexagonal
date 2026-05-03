package com.waterconnect.domain.model.aggregate;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import com.waterconnect.domain.event.DomainEvent;
import com.waterconnect.domain.event.ServiceConnectionActivatedEvent;
import com.waterconnect.domain.event.ServicePointApprovedEvent;
import com.waterconnect.domain.event.ServicePointConnectionRequestedEvent;
import com.waterconnect.domain.exception.BusinessRuleViolationException;
import com.waterconnect.domain.model.entity.WaterMeter;
import com.waterconnect.domain.model.enums.ServicePointStatus;

/**
 * ServicePoint Aggregate Root
 */
public class ServicePoint {

    private UUID servicePointId;
    private UUID customerId;
    private UUID connectorId;
    private WaterMeter meter;
    private ServicePointStatus status;
    private Instant requestedAt;
    private Instant activatedAt;

    private final List<DomainEvent> events = new ArrayList<>();

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
        servicePoint.events.add(new ServicePointConnectionRequestedEvent(servicePointId));

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

        this.events.add(new ServicePointApprovedEvent(servicePointId));
    }

    /**
     * Activate the service point and set the provided water meter
     * @param meter - service point water meter
     * @throws BusinessRuleViolationException if the current state is not APPROVED
     */
    public void activate(WaterMeter meter) throws BusinessRuleViolationException {
        if (this.status != ServicePointStatus.APPROVED) {
            throw new BusinessRuleViolationException("Only APPROVED service point can be activated, current: " + this.status);
        }

        this.status = ServicePointStatus.ACTIVE;
        this.meter = meter;
        this.activatedAt =  Instant.now();

        this.events.add(new ServiceConnectionActivatedEvent(this.servicePointId));
    }

    /**
     * Record the current value of the service point water meter
     */
    public void recordMeterReading(double readingM3) throws BusinessRuleViolationException {
        // Validate status
        if (this.status != ServicePointStatus.ACTIVE) {
            throw new BusinessRuleViolationException("Only ACTIVE service point can record meter, current: " + this.status);
        }
        // Validate if water meter is present
        if (this.meter == null) {
            throw new BusinessRuleViolationException("No meter found for this service point, ID: " + this.servicePointId);
        }

        this.meter.recordReading(readingM3);
    }

    public void suspend() {
        if (this.status != ServicePointStatus.ACTIVE) {
            throw new IllegalStateException(
                    "Only ACTIVE service points can be suspended, current: " + this.status
            );
        }
        this.status = ServicePointStatus.SUSPENDED;
    }

    public void disconnect() {
        if (this.status != ServicePointStatus.ACTIVE && this.status != ServicePointStatus.SUSPENDED) {
            throw new IllegalStateException(
                    "Only ACTIVE or SUSPENDED service points can be disconnected, current: " + this.status
            );
        }
        this.status = ServicePointStatus.DISCONNECTED;
    }

    public void clearEvents() {
        this.events.clear();
    }

    public List<DomainEvent> getEvents() {
        return Collections.unmodifiableList(this.events);
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
