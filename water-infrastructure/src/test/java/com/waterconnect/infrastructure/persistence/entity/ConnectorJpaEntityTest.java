package com.waterconnect.infrastructure.persistence.entity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

import com.waterconnect.domain.model.aggregate.Connector;
import com.waterconnect.domain.model.aggregate.Pipe;
import com.waterconnect.domain.model.enums.ConnectorType;
import com.waterconnect.domain.model.enums.PipeMaterial;
import com.waterconnect.domain.model.valueobject.GeoPoint;
import com.waterconnect.domain.model.valueobject.GeoSegment;

class ConnectorJpaEntityTest {

    @Test
    void toDomain_rehydratesConnectorIncludingOrderedPipeIds() {
        Connector original = Connector.create(
                ConnectorType.ELBOW,
                100,
                PipeMaterial.HDPE,
                new GeoPoint(41.1579, -8.6291)
        );
        Pipe firstPipe = Pipe.planNew(100, 10.0, 8.0, PipeMaterial.HDPE, segment());
        Pipe secondPipe = Pipe.planNew(100, 20.0, 10.0, PipeMaterial.HDPE, segment());
        original.connectPipe(firstPipe);
        original.connectPipe(secondPipe);

        ConnectorJpaEntity entity = ConnectorJpaEntity.fromDomain(original);
        Connector restored = entity.toDomain();

        assertThat(restored.getConnectorId()).isEqualTo(original.getConnectorId());
        assertThat(restored.getConnectorType()).isEqualTo(original.getConnectorType());
        assertThat(restored.getMaxConnections()).isEqualTo(original.getMaxConnections());
        assertThat(restored.getDiameterMm()).isEqualTo(original.getDiameterMm());
        assertThat(restored.getMaterial()).isEqualTo(original.getMaterial());
        assertThat(restored.getLocation()).isEqualTo(original.getLocation());
        assertThat(restored.getConnectedPipeIds()).containsExactlyElementsOf(original.getConnectedPipeIds());
    }

    private static GeoSegment segment() {
        return new GeoSegment(new GeoPoint(41.0, -8.0), new GeoPoint(41.001, -8.001));
    }
}
