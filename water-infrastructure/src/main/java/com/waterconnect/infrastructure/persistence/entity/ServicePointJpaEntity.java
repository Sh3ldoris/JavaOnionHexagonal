package com.waterconnect.infrastructure.persistence.entity;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

import com.waterconnect.domain.model.aggregate.ServicePoint;
import com.waterconnect.domain.model.enums.ServicePointStatus;
import com.waterconnect.infrastructure.persistence.entity.factory.ServicePointFactory;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

/**
 * JPA entity for ServicePoint. Maps to the database table.
 */
@Entity
@Table(name = "service_point")
public class ServicePointJpaEntity {

    @Id
    private UUID servicePointId;
    private UUID customerId;
    private UUID connectorId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "meter_id", referencedColumnName = "meterId")
    private WaterMeterJpaEntity meter;

    @Enumerated(EnumType.STRING)
    private ServicePointStatus status;
    private Instant requestedAt;
    private Instant activatedAt;

    protected ServicePointJpaEntity() {}

    public static ServicePointJpaEntity fromDomain(ServicePoint servicePoint) {
        if (Objects.isNull(servicePoint)) {
            return null;
        }

        var entity = new ServicePointJpaEntity();
        entity.servicePointId = servicePoint.getServicePointId();
        entity.customerId = servicePoint.getCustomerId();
        entity.connectorId = servicePoint.getConnectorId();
        entity.meter = WaterMeterJpaEntity.fromDomain(servicePoint.getMeter());
        entity.status = servicePoint.getStatus();
        entity.requestedAt = servicePoint.getRequestedAt();
        entity.activatedAt = servicePoint.getActivatedAt();

        return entity;
    }

    public ServicePoint toDomain() {
        return ServicePointFactory.createServicePoint(
                this.servicePointId,
                this.customerId,
                this.connectorId,
                this.meter,
                this.status,
                this.requestedAt,
                this.activatedAt
        );
    }


    public UUID getServicePointId() {
        return servicePointId;
    }

    public void setServicePointId(UUID servicePointId) {
        this.servicePointId = servicePointId;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public void setCustomerId(UUID customerId) {
        this.customerId = customerId;
    }

    public UUID getConnectorId() {
        return connectorId;
    }

    public void setConnectorId(UUID connectorId) {
        this.connectorId = connectorId;
    }

    public WaterMeterJpaEntity getMeter() {
        return meter;
    }

    public void setMeter(WaterMeterJpaEntity meter) {
        this.meter = meter;
    }

    public ServicePointStatus getStatus() {
        return status;
    }

    public void setStatus(ServicePointStatus status) {
        this.status = status;
    }

    public Instant getRequestedAt() {
        return requestedAt;
    }

    public void setRequestedAt(Instant requestedAt) {
        this.requestedAt = requestedAt;
    }

    public Instant getActivatedAt() {
        return activatedAt;
    }

    public void setActivatedAt(Instant activatedAt) {
        this.activatedAt = activatedAt;
    }
}
