package com.waterconnect.domain.model.entity;

import java.time.Instant;
import java.util.UUID;

import com.waterconnect.domain.exception.BusinessRuleViolationException;

/**
 * WaterMeter — Entity within ServicePoint aggregate.
 */
public class WaterMeter {

    private UUID meterId;
    private String serialNumber;
    private Instant installedAt;
    private double lastReadingM3;
    private Instant lastReadingAt;

    protected WaterMeter() {}

    public static WaterMeter install(String serialNumber, double lastReadingM3) {
        var meter = new WaterMeter();
        meter.meterId = UUID.randomUUID();
        meter.serialNumber =  serialNumber;
        meter.lastReadingM3 = lastReadingM3;
        meter.installedAt = Instant.now();

        return meter;
    }

    public void recordReading(double readingM3) throws BusinessRuleViolationException {
        if (readingM3 < 0) {
            throw new BusinessRuleViolationException("readingM3 must be greater than 0. Was: " + readingM3);
        }
        // Validate if the current reading is greater than was reading before
        if (readingM3 < this.lastReadingM3) {
            throw new BusinessRuleViolationException(
                    "Reading %.3f cannot be less than previous reading %.3f"
                            .formatted(readingM3, this.lastReadingM3)
            );
        }
        // Set the values
        this.lastReadingM3 = readingM3;
        this.lastReadingAt = Instant.now();
    }

    public UUID getMeterId() {
        return meterId;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public Instant getInstalledAt() {
        return installedAt;
    }

    public double getLastReadingM3() {
        return lastReadingM3;
    }

    public Instant getLastReadingAt() {
        return lastReadingAt;
    }

    protected void setMeterId(UUID meterId) {
        this.meterId = meterId;
    }

    protected void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    protected void setInstalledAt(Instant installedAt) {
        this.installedAt = installedAt;
    }

    protected void setLastReadingM3(double lastReadingM3) {
        this.lastReadingM3 = lastReadingM3;
    }

    protected void setLastReadingAt(Instant lastReadingAt) {
        this.lastReadingAt = lastReadingAt;
    }
}
