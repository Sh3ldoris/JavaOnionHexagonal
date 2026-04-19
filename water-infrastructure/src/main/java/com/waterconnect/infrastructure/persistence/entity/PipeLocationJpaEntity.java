package com.waterconnect.infrastructure.persistence.entity;

import java.util.UUID;

import com.waterconnect.domain.model.valueobject.GeoPoint;
import com.waterconnect.domain.model.valueobject.GeoSegment;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "pipe_locations")
public class PipeLocationJpaEntity {

    @Id
    @Column(name = "pipe_id", nullable = false)
    private java.util.UUID pipeId;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "pipe_id", nullable = false, foreignKey = @ForeignKey(name = "fk_pipe_locations_pipe"))
    private PipeJpaEntity pipe;

    @Column(name = "start_latitude", nullable = false)
    private double startLatitude;

    @Column(name = "start_longitude", nullable = false)
    private double startLongitude;

    @Column(name = "end_latitude", nullable = false)
    private double endLatitude;

    @Column(name = "end_longitude", nullable = false)
    private double endLongitude;

    protected PipeLocationJpaEntity() {}

    public static PipeLocationJpaEntity fromDomain(GeoSegment location) {
        var entity = new PipeLocationJpaEntity();
        entity.startLatitude = location.start().latitude();
        entity.startLongitude = location.start().longitude();
        entity.endLatitude = location.end().latitude();
        entity.endLongitude = location.end().longitude();
        return entity;
    }

    public GeoSegment toDomain() {
        return new GeoSegment(
                new GeoPoint(startLatitude, startLongitude),
                new GeoPoint(endLatitude, endLongitude)
        );
    }

    public void setPipe(PipeJpaEntity pipe) {
        this.pipe = pipe;
    }

    public UUID getPipeId() {
        return pipeId;
    }

    public PipeJpaEntity getPipe() {
        return pipe;
    }

    public double getStartLatitude() {
        return startLatitude;
    }

    public double getStartLongitude() {
        return startLongitude;
    }

    public double getEndLatitude() {
        return endLatitude;
    }

    public double getEndLongitude() {
        return endLongitude;
    }
}
