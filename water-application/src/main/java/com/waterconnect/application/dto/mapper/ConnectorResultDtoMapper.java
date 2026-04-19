package com.waterconnect.application.dto.mapper;

import java.util.ArrayList;
import java.util.UUID;

import com.waterconnect.application.dto.ConnectorResultDto;
import com.waterconnect.application.dto.enums.ConnectorType;
import com.waterconnect.application.dto.enums.PipeMaterial;
import com.waterconnect.domain.model.aggregate.Connector;

public class ConnectorResultDtoMapper {

    private ConnectorResultDtoMapper() {}

    public static ConnectorResultDto fromDomain(Connector connector) {
        var location = GeoPointDtoMapper.fromDomain(connector.getLocation());

        return new ConnectorResultDto(
            connector.getConnectorId(),
                ConnectorType.fromString(connector.getConnectorType().name()),
                connector.getDiameterMm(),
                PipeMaterial.fromString(connector.getMaterial().name()),
                location,
                new ArrayList<UUID>(connector.getConnectedPipeIds())
        );
    }
}
