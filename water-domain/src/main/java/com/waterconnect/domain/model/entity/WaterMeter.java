package com.waterconnect.domain.model.entity;

import java.time.Instant;
import java.util.UUID;

/**
 * WaterMeter — Entity within ServicePoint aggregate.
 *
 * TODO: Implement in Level 2
 * - Method: recordReading(double readingM3) — must be >= lastReadingM3
 * - Update lastReadingM3 and lastReadingAt
 */
public class WaterMeter {

    private UUID meterId;
    private String serialNumber;
    private Instant installedAt;
    private double lastReadingM3;
    private Instant lastReadingAt;

    protected WaterMeter() {}

    // TODO: Implement
}
