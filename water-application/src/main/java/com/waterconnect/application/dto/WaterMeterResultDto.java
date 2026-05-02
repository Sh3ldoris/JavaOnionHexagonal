package com.waterconnect.application.dto;

import java.time.Instant;

public record WaterMeterResultDto(
        String serialNumber,
        Instant installedAt,
        double lastReadingM3,
        Instant lastReadingAt
) {
}
