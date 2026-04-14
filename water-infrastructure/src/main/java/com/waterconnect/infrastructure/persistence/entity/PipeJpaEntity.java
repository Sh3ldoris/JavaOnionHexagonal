package com.waterconnect.infrastructure.persistence.entity;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

import com.waterconnect.domain.model.aggregate.Pipe;
import com.waterconnect.domain.model.enums.PipeMaterial;
import com.waterconnect.domain.model.enums.PipeStatus;
import com.waterconnect.infrastructure.persistence.entity.factory.PipeFactory;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

/**
 * JPA entity for Pipe. Maps to the database table.
 */
@Entity
@Table(name = "pipes")
public class PipeJpaEntity {

    @Id
    private UUID pipeId;

    @Enumerated(EnumType.STRING)
    private PipeMaterial material;

    private int diameterMm;
    private double lengthMeters;
    private double pressureRatingBar;

    @Enumerated(EnumType.STRING)
    private PipeStatus status;

    private Instant installedAt;

    @OneToOne(mappedBy = "pipe", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY, optional = false)
    private PipeLocationJpaEntity location;

    // JPA requires no-arg constructor
    protected PipeJpaEntity() {}

    /**
     * Factory from Pipe domain object.
     */
    public static PipeJpaEntity fromDomain(Pipe pipe) {
        var entity = new PipeJpaEntity();
        entity.pipeId = pipe.getPipeId();
        entity.material = pipe.getMaterial();
        entity.diameterMm = pipe.getDiameterMm();
        entity.lengthMeters = pipe.getLengthMeters();
        entity.pressureRatingBar = pipe.getPressureRatingBar();
        entity.status = pipe.getStatus();
        entity.installedAt = pipe.getInstalledAt();

        var locationEntity = PipeLocationJpaEntity.fromDomain(pipe.getLocation());
        locationEntity.setPipe(entity);
        entity.location = locationEntity;

        return entity;
    }

    /**
     * Factory of Pipe domain object.
     */
    public Pipe toDomain() {
        return PipeFactory.createPipe(
                pipeId,
                material,
                diameterMm,
                lengthMeters,
                pressureRatingBar,
                status,
                installedAt,
                Objects.requireNonNull(location, "location must not be null").toDomain()
        );
    }

    public UUID getPipeId() {
        return pipeId;
    }

    public void setPipeId(UUID pipeId) {
        this.pipeId = pipeId;
    }

    public PipeMaterial getMaterial() {
        return material;
    }

    public void setMaterial(PipeMaterial material) {
        this.material = material;
    }

    public int getDiameterMm() {
        return diameterMm;
    }

    public void setDiameterMm(int diameterMm) {
        this.diameterMm = diameterMm;
    }

    public double getLengthMeters() {
        return lengthMeters;
    }

    public void setLengthMeters(double lengthMeters) {
        this.lengthMeters = lengthMeters;
    }

    public double getPressureRatingBar() {
        return pressureRatingBar;
    }

    public void setPressureRatingBar(double pressureRatingBar) {
        this.pressureRatingBar = pressureRatingBar;
    }

    public PipeStatus getStatus() {
        return status;
    }

    public void setStatus(PipeStatus status) {
        this.status = status;
    }

    public Instant getInstalledAt() {
        return installedAt;
    }

    public void setInstalledAt(Instant installedAt) {
        this.installedAt = installedAt;
    }

    public PipeLocationJpaEntity getLocation() {
        return location;
    }

    public void setLocation(PipeLocationJpaEntity location) {
        this.location = location;
    }
}
