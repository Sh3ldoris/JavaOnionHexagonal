package com.waterconnect.application.dto;

import java.util.List;
import java.util.UUID;

import com.waterconnect.application.dto.enums.ConnectorType;
import com.waterconnect.application.dto.enums.PipeMaterial;

public record ConnectorResultDto(
        UUID connectorId,
        ConnectorType connectorType,
        int diameterMm,
        PipeMaterial material,
        GeoPointDto location,
        List<UUID> connectedPipeIds
) {
}
