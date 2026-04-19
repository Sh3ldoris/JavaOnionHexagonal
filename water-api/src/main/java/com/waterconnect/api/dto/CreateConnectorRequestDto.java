package com.waterconnect.api.dto;

import com.waterconnect.api.dto.enums.ConnectorType;
import com.waterconnect.api.dto.enums.PipeMaterial;
import jakarta.validation.constraints.NotNull;

public record CreateConnectorRequestDto(
        @NotNull ConnectorType connectorType,
        @NotNull Integer diameterMm,
        @NotNull PipeMaterial material,
        @NotNull Double latitude,
        @NotNull Double longitude
) {
}
