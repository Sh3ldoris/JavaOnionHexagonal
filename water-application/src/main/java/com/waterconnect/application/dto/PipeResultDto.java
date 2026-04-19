package com.waterconnect.application.dto;

import java.time.Instant;
import java.util.UUID;

import com.waterconnect.application.dto.enums.PipeMaterial;
import com.waterconnect.application.dto.enums.PipeStatus;

public record PipeResultDto(
        UUID pipeId,
        PipeMaterial material,
        int diameterMm,
        double lengthMeters,
        double pressureRatingBar,
        PipeStatus status,
        Instant installedAt,
        GeoSegmentDto location
) {
}
