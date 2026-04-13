package com.waterconnect.application.usecase;

import java.util.UUID;

import com.waterconnect.application.command.CreateConnectorCommand;
import com.waterconnect.application.dto.mapper.GeoPointDtoMapper;
import com.waterconnect.domain.model.aggregate.Connector;
import com.waterconnect.domain.model.enums.ConnectorType;
import com.waterconnect.domain.model.enums.PipeMaterial;
import com.waterconnect.domain.model.valueobject.GeoPoint;
import com.waterconnect.domain.port.outbound.ConnectorRepository;

/**
 * Use case: Create a connector between pipes.
 */
public class CreateConnectorUseCase {

    private final ConnectorRepository connectorRepository;

    public CreateConnectorUseCase(ConnectorRepository connectorRepository) {
        this.connectorRepository = connectorRepository;
    }

    public UUID create(CreateConnectorCommand command) {
        GeoPoint location = GeoPointDtoMapper.mapToGeoPoint(command.location());

        // Create a new Connector
        var connector = Connector.create(ConnectorType.fromString(command.connectorType()), command.diameterMm(),
                PipeMaterial.fromString(command.pipeMaterial()), location);

        // Save the new Connector
        this.connectorRepository.save(connector);

        return connector.getConnectorId();
    }
}
