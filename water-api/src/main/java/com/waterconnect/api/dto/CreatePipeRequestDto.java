package com.waterconnect.api.dto;

import com.waterconnect.api.dto.enums.PipeMaterial;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record CreatePipeRequestDto(
        @NotNull PipeMaterial material,
        @NotNull @Min(15) @Max(1200) Integer diameterMm,
        @NotNull Integer lengthMeters,
        @NotNull Integer pressureRatingBar,
        @NotNull Integer startLatitude,
        @NotNull Integer startLongitude,
        @NotNull Integer endLatitude,
        @NotNull Integer endLongitude
) {
}
