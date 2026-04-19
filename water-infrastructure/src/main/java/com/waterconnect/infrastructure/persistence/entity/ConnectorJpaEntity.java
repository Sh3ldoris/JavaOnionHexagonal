package com.waterconnect.infrastructure.persistence.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.waterconnect.domain.model.aggregate.Connector;
import com.waterconnect.domain.model.enums.ConnectorType;
import com.waterconnect.domain.model.enums.PipeMaterial;
import com.waterconnect.domain.model.valueobject.GeoPoint;
import com.waterconnect.infrastructure.persistence.entity.factory.ConnectorFactory;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OrderColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "connectors")
public class ConnectorJpaEntity {

    @Id
    private UUID connectorId;

    @Enumerated(EnumType.STRING)
    private ConnectorType connectorType;

    private int maxConnections;

    private int diameterMm;

    @Enumerated(EnumType.STRING)
    private PipeMaterial material;

    private double latitude;

    private double longitude;

    @ElementCollection
    @CollectionTable(name = "connector_connected_pipes", joinColumns = @JoinColumn(name = "connector_id"))
    @Column(name = "pipe_id", nullable = false)
    @OrderColumn(name = "list_idx")
    private List<UUID> connectedPipeIds = new ArrayList<>();

    protected ConnectorJpaEntity() {}

    public static ConnectorJpaEntity fromDomain(Connector connector) {
        var entity = new ConnectorJpaEntity();
        entity.connectorId = connector.getConnectorId();
        entity.connectorType = connector.getConnectorType();
        entity.maxConnections = connector.getMaxConnections();
        entity.diameterMm = connector.getDiameterMm();
        entity.material = connector.getMaterial();
        entity.latitude = connector.getLocation().latitude();
        entity.longitude = connector.getLocation().longitude();
        entity.connectedPipeIds = new ArrayList<>(connector.getConnectedPipeIds());
        return entity;
    }

    public Connector toDomain() {
        return ConnectorFactory.createConnector(
                connectorId,
                connectorType,
                maxConnections,
                diameterMm,
                material,
                new GeoPoint(latitude, longitude),
                new ArrayList<>(connectedPipeIds)
        );
    }

    public UUID getConnectorId() {
        return connectorId;
    }

    public void setConnectorId(UUID connectorId) {
        this.connectorId = connectorId;
    }

    public ConnectorType getConnectorType() {
        return connectorType;
    }

    public void setConnectorType(ConnectorType connectorType) {
        this.connectorType = connectorType;
    }

    public int getMaxConnections() {
        return maxConnections;
    }

    public void setMaxConnections(int maxConnections) {
        this.maxConnections = maxConnections;
    }

    public int getDiameterMm() {
        return diameterMm;
    }

    public void setDiameterMm(int diameterMm) {
        this.diameterMm = diameterMm;
    }

    public PipeMaterial getMaterial() {
        return material;
    }

    public void setMaterial(PipeMaterial material) {
        this.material = material;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public List<UUID> getConnectedPipeIds() {
        return connectedPipeIds;
    }

    public void setConnectedPipeIds(List<UUID> connectedPipeIds) {
        this.connectedPipeIds = connectedPipeIds;
    }
}
