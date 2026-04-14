package com.waterconnect.infrastructure.persistence.entity.factory;

import java.util.List;
import java.util.UUID;

import com.waterconnect.domain.model.aggregate.Connector;
import com.waterconnect.domain.model.enums.ConnectorType;
import com.waterconnect.domain.model.enums.PipeMaterial;
import com.waterconnect.domain.model.valueobject.GeoPoint;

public class ConnectorFactory extends Connector {

    private ConnectorFactory() {}

    public static Connector createConnector(
            UUID connectorId,
            ConnectorType connectorType,
            int maxConnections,
            int diameterMm,
            PipeMaterial material,
            GeoPoint location,
            List<UUID> connectedPipeIds
    ) {
        var connector = new ConnectorFactory();
        connector.setConnectorId(connectorId);
        connector.setConnectorType(connectorType);
        connector.setMaxConnections(maxConnections);
        connector.setDiameterMm(diameterMm);
        connector.setMaterial(material);
        connector.setLocation(location);
        connector.setConnectedPipeIds(connectedPipeIds);
        return connector;
    }
}
