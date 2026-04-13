package com.waterconnect.application.command;

import com.waterconnect.application.dto.GeoPointDto;

public record CreateConnectorCommand(String connectorType, int diameterMm, String pipeMaterial, GeoPointDto location) {
}
