package com.waterconnect.application.dto;

import java.time.Instant;
import java.util.UUID;

import com.waterconnect.application.dto.enums.ServicePointStatus;

public record ServicePointResultDto(
        UUID servicePointId,
        UUID customerId,
        UUID connectorId,
        WaterMeterResultDto meter,
        ServicePointStatus status,
        Instant requestedAt,
        Instant activatedAt
) {
}
