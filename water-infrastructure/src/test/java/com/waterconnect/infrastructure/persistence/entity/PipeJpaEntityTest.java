package com.waterconnect.infrastructure.persistence.entity;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import com.waterconnect.domain.model.aggregate.Pipe;
import com.waterconnect.domain.model.enums.PipeMaterial;
import com.waterconnect.domain.model.valueobject.GeoPoint;
import com.waterconnect.domain.model.valueobject.GeoSegment;

class PipeJpaEntityTest {

    @Test
    void toDomain_rehydratesPipeAndLocationFromPersistedFields() {
        Pipe original = Pipe.planNew(
                200,
                120.5,
                16.0,
                PipeMaterial.HDPE,
                new GeoSegment(
                        new GeoPoint(41.1579, -8.6291),
                        new GeoPoint(41.1584, -8.6282)
                )
        );

        PipeJpaEntity entity = PipeJpaEntity.fromDomain(original);
        Pipe restored = entity.toDomain();

        assertThat(restored.getPipeId()).isEqualTo(original.getPipeId());
        assertThat(restored.getMaterial()).isEqualTo(original.getMaterial());
        assertThat(restored.getDiameterMm()).isEqualTo(original.getDiameterMm());
        assertThat(restored.getLengthMeters()).isEqualTo(original.getLengthMeters());
        assertThat(restored.getPressureRatingBar()).isEqualTo(original.getPressureRatingBar());
        assertThat(restored.getStatus()).isEqualTo(original.getStatus());
        assertThat(restored.getInstalledAt()).isEqualTo(original.getInstalledAt());

        assertThat(restored.getLocation().start().latitude()).isEqualTo(41.1579);
        assertThat(restored.getLocation().start().longitude()).isEqualTo(-8.6291);
        assertThat(restored.getLocation().end().latitude()).isEqualTo(41.1584);
        assertThat(restored.getLocation().end().longitude()).isEqualTo(-8.6282);
    }
}
