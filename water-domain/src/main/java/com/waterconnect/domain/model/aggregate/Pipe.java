package com.waterconnect.domain.model.aggregate;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

import com.waterconnect.domain.exception.BusinessRuleViolationException;
import com.waterconnect.domain.model.enums.PipeMaterial;
import com.waterconnect.domain.model.enums.PipeStatus;
import com.waterconnect.domain.model.valueobject.GeoSegment;

/**
 * Pipe Aggregate Root.
 */
public class Pipe {

    private UUID pipeId;
    private PipeMaterial material;
    private int diameterMm;
    private double lengthMeters;
    private double pressureRatingBar;
    private PipeStatus status;
    private Instant installedAt;
    private GeoSegment location;

    protected Pipe() {}

    public void install() {
        if (this.status != PipeStatus.PLANNED) {
            throw new BusinessRuleViolationException("Pipe status must be PLANNED, but it is " + this.status);
        }

        this.installedAt = Instant.now();
        this.status = PipeStatus.INSTALLED;
    }

    public void activate() {
        if (this.status != PipeStatus.INSTALLED) {
            throw new BusinessRuleViolationException("Pipe status must be INSTALLED, but it is " + this.status);
        }

        this.status = PipeStatus.ACTIVE;
    }

    public void decommission() {
        if (this.status != PipeStatus.ACTIVE) {
            throw new BusinessRuleViolationException("Pipe status must be ACTIVE, but it is " + this.status);
        }

        this.status = PipeStatus.DECOMMISSIONED;
    }

    public UUID getPipeId() {
        return pipeId;
    }

    public PipeMaterial getMaterial() {
        return material;
    }

    public int getDiameterMm() {
        return diameterMm;
    }

    public double getLengthMeters() {
        return lengthMeters;
    }

    public double getPressureRatingBar() {
        return pressureRatingBar;
    }

    public PipeStatus getStatus() {
        return status;
    }

    public Instant getInstalledAt() {
        return installedAt;
    }

    public GeoSegment getLocation() {
        return location;
    }

    public static Pipe planNew(int diameterMm, double lengthMeters, double pressureRatingBar, PipeMaterial pipeMaterial, GeoSegment location) {
        // Create a new Pipe domain object
        var pipe = new Pipe();

        // Check the given material
        Objects.requireNonNull(pipeMaterial, "pipeMaterial must not be null");
        // Check the given location
        Objects.requireNonNull(location, "location must not be null");
        // Check the planned diameter
        if (diameterMm < 15 || diameterMm > 1200) {
            throw new BusinessRuleViolationException("diameterMm must be between 15 and 1200!");
        }
        // Check the pressure rating
        if (pressureRatingBar <= 0) {
            throw new BusinessRuleViolationException("pressureRatingBar must be greater than 0!");
        }

        // Set attributes from parameters
        pipe.diameterMm = diameterMm;
        pipe.lengthMeters = lengthMeters;
        pipe.pressureRatingBar = pressureRatingBar;
        pipe.material = pipeMaterial;
        pipe.location = location;

        // Set other attributes
        pipe.pipeId = UUID.randomUUID();
        pipe.status = PipeStatus.PLANNED;

        return pipe;
    }

    protected void setPipeId(UUID pipeId) {
        this.pipeId = pipeId;
    }

    protected void setMaterial(PipeMaterial material) {
        this.material = material;
    }

    protected void setDiameterMm(int diameterMm) {
        this.diameterMm = diameterMm;
    }

    protected void setLengthMeters(double lengthMeters) {
        this.lengthMeters = lengthMeters;
    }

    protected void setPressureRatingBar(double pressureRatingBar) {
        this.pressureRatingBar = pressureRatingBar;
    }

    protected void setStatus(PipeStatus status) {
        this.status = status;
    }

    protected void setInstalledAt(Instant installedAt) {
        this.installedAt = installedAt;
    }

    protected void setLocation(GeoSegment location) {
        this.location = location;
    }
}
