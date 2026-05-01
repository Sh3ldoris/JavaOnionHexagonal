package com.waterconnect.infrastructure.persistence.entity;

import java.time.Instant;
import java.util.UUID;

import com.waterconnect.domain.model.entity.WaterMeter;
import com.waterconnect.infrastructure.persistence.entity.factory.WaterMeterFactory;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table("water_meter")
public class WaterMeterJpaEntity {

    @Id
    private UUID meterId;
    private String serialNumber;
    private Instant installedAt;
    private double lastReadingM3;
    private Instant lastReadingAt;

    protected WaterMeterJpaEntity() {
    }

    public static WaterMeterJpaEntity fromDomain(WaterMeter meter) {
        var entity = new WaterMeterJpaEntity();

        entity.meterId = meter.getMeterId();
        entity.serialNumber = meter.getSerialNumber();
        entity.installedAt = meter.getInstalledAt();
        entity.lastReadingM3 = meter.getLastReadingM3();
        entity.lastReadingAt = meter.getLastReadingAt();

        return entity;
    }

    public WaterMeter toDomain() {
        return WaterMeterFactory.createWaterMeter(
                this.meterId,
                this.serialNumber,
                this.installedAt,
                this.lastReadingM3,
                this.lastReadingAt
        );
    }


    public UUID getMeterId() {
        return meterId;
    }

    public void setMeterId(UUID meterId) {
        this.meterId = meterId;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public Instant getInstalledAt() {
        return installedAt;
    }

    public void setInstalledAt(Instant installedAt) {
        this.installedAt = installedAt;
    }

    public double getLastReadingM3() {
        return lastReadingM3;
    }

    public void setLastReadingM3(double lastReadingM3) {
        this.lastReadingM3 = lastReadingM3;
    }

    public Instant getLastReadingAt() {
        return lastReadingAt;
    }

    public void setLastReadingAt(Instant lastReadingAt) {
        this.lastReadingAt = lastReadingAt;
    }
}
