package com.waterconnect.domain.model.aggregate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import com.waterconnect.domain.exception.BusinessRuleViolationException;
import com.waterconnect.domain.model.enums.ConnectorType;
import com.waterconnect.domain.model.enums.PipeMaterial;
import com.waterconnect.domain.model.valueobject.GeoPoint;

/**
 * Connector root
 */
public class Connector {

    private UUID connectorId;
    private ConnectorType connectorType;
    private int maxConnections;
    private int diameterMm;
    private PipeMaterial material;
    private GeoPoint location;
    private final List<UUID> connectedPipeIds = new ArrayList<>();

    protected Connector() {
    }

    public List<UUID> getConnectedPipeIds() {
        return Collections.unmodifiableList(this.connectedPipeIds);
    }

    public void connectPipe(Pipe pipe) {
        // Check max connections
        if (this.getConnectedPipeIds().size() >= this.getMaxConnections()) {
            throw new BusinessRuleViolationException("The maximum number of pipes exceeded!");
        }
        // Check parameters
        Objects.requireNonNull(pipe, "pipeId must not be null!");

        // Check if pipe diameter falls into 20% of connector diameter
        var pipeDiameterMm = pipe.getDiameterMm();

        double lowerBound = this.diameterMm * 0.8;
        double upperBound = this.diameterMm * 1.2;

        if (pipeDiameterMm < lowerBound || pipeDiameterMm > upperBound) {
            throw new BusinessRuleViolationException("Pipe diameter must be within ±20% of connector diameter!");
        }

        // Add the pipe as connected to connector
        this.connectedPipeIds.add(pipe.getPipeId());
    }

    public int getMaxConnections() {
        return this.maxConnections;
    }

    public UUID getConnectorId() {
        return connectorId;
    }

    public ConnectorType getConnectorType() {
        return connectorType;
    }

    public int getDiameterMm() {
        return diameterMm;
    }

    public PipeMaterial getMaterial() {
        return material;
    }

    public GeoPoint getLocation() {
        return location;
    }

    public static Connector create(ConnectorType connectorType, int diameterMm, PipeMaterial material, GeoPoint location) {
        // Create a new domain object
        var connector = new Connector();

        // Check the given values
        Objects.requireNonNull(connectorType, "connectorType must not be null!");
        Objects.requireNonNull(material, "material must not be null!");
        Objects.requireNonNull(location, "location must not be null!");

        if (diameterMm <= 0) {
            throw new BusinessRuleViolationException("diameterMm must be greater than 0!");
        }

        // Set attributes from parameters
        connector.connectorType = connectorType;
        connector.diameterMm = diameterMm;
        connector.material = material;
        connector.location = location;

        // Set other attributes
        connector.connectorId = UUID.randomUUID();
        connector.maxConnections = connectorType == ConnectorType.TEE ? 3 : 2;

        return connector;
    }

    protected void setConnectorId(UUID connectorId) {
        this.connectorId = connectorId;
    }

    protected void setConnectorType(ConnectorType connectorType) {
        this.connectorType = connectorType;
    }

    protected void setMaxConnections(int maxConnections) {
        this.maxConnections = maxConnections;
    }

    protected void setDiameterMm(int diameterMm) {
        this.diameterMm = diameterMm;
    }

    protected void setMaterial(PipeMaterial material) {
        this.material = material;
    }

    protected void setLocation(GeoPoint location) {
        this.location = location;
    }

    protected void setConnectedPipeIds(List<UUID> connectedPipeIds) {
        this.connectedPipeIds.clear();
        this.connectedPipeIds.addAll(connectedPipeIds);
    }
}
