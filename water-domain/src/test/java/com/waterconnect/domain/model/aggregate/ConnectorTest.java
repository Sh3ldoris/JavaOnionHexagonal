package com.waterconnect.domain.model.aggregate;

import static com.waterconnect.domain.model.aggregate.support.AggregateTestFixtures.geoPoint;
import static com.waterconnect.domain.model.aggregate.support.AggregateTestFixtures.pipeWithDiameterMm;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.waterconnect.domain.exception.BusinessRuleViolationException;
import com.waterconnect.domain.model.enums.ConnectorType;
import com.waterconnect.domain.model.enums.PipeMaterial;

class ConnectorTest {

    @Test
    void create_setsIdentityTypeMaterialLocationDiameter_andMaxConnectionsForTee() {
        var location = geoPoint();
        var connector = Connector.create(ConnectorType.TEE, 50, PipeMaterial.HDPE, location);

        assertThat(connector.getConnectorId()).isNotNull();
        assertThat(connector.getConnectorType()).isEqualTo(ConnectorType.TEE);
        assertThat(connector.getDiameterMm()).isEqualTo(50);
        assertThat(connector.getMaterial()).isEqualTo(PipeMaterial.HDPE);
        assertThat(connector.getLocation()).isEqualTo(location);
        assertThat(connector.getMaxConnections()).isEqualTo(3);
    }

    @Test
    void create_setsMaxConnectionsToTwo_forNonTee() {
        var connector = Connector.create(ConnectorType.ELBOW, 40, PipeMaterial.PVC, geoPoint());
        assertThat(connector.getMaxConnections()).isEqualTo(2);
    }

    @Test
    void create_rejectsNullConnectorType() {
        assertThatThrownBy(() -> Connector.create(null, 10, PipeMaterial.PVC, geoPoint()))
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("connectorType");
    }

    @Test
    void create_rejectsNullMaterial() {
        assertThatThrownBy(() -> Connector.create(ConnectorType.COUPLING, 10, null, geoPoint()))
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("material");
    }

    @Test
    void create_rejectsNullLocation() {
        assertThatThrownBy(() -> Connector.create(ConnectorType.COUPLING, 10, PipeMaterial.PVC, null))
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("location");
    }

    @Test
    void create_rejectsNonPositiveDiameter() {
        assertThatThrownBy(() -> Connector.create(ConnectorType.VALVE, 0, PipeMaterial.STEEL, geoPoint()))
                .isInstanceOf(BusinessRuleViolationException.class)
                .hasMessageContaining("diameterMm");
    }

    @Test
    void connectPipe_acceptsPipeAtTwentyPercentBoundsInclusive() {
        var connector = Connector.create(ConnectorType.ELBOW, 100, PipeMaterial.COPPER, geoPoint());
        var low = pipeWithDiameterMm(80);
        var high = pipeWithDiameterMm(120);

        connector.connectPipe(low);
        connector.connectPipe(high);

        assertThat(connector.getConnectedPipeIds()).containsExactly(low.getPipeId(), high.getPipeId());
    }

    @Test
    void connectPipe_rejectsPipeBelowLowerBound() {
        var connector = Connector.create(ConnectorType.REDUCER, 100, PipeMaterial.STEEL, geoPoint());
        var pipe = pipeWithDiameterMm(79);

        assertThatThrownBy(() -> connector.connectPipe(pipe))
                .isInstanceOf(BusinessRuleViolationException.class)
                .hasMessageContaining("Pipe diameter");
    }

    @Test
    void connectPipe_rejectsPipeAboveUpperBound() {
        var connector = Connector.create(ConnectorType.COUPLING, 100, PipeMaterial.HDPE, geoPoint());
        var pipe = pipeWithDiameterMm(121);

        assertThatThrownBy(() -> connector.connectPipe(pipe))
                .isInstanceOf(BusinessRuleViolationException.class)
                .hasMessageContaining("Pipe diameter");
    }

    @Test
    void connectPipe_rejectsNullPipe() {
        var connector = Connector.create(ConnectorType.ELBOW, 100, PipeMaterial.PVC, geoPoint());

        assertThatThrownBy(() -> connector.connectPipe(null))
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("pipeId");
    }

    @Test
    void connectPipe_enforcesMaxTwo_forNonTee() {
        var connector = Connector.create(ConnectorType.VALVE, 100, PipeMaterial.PVC, geoPoint());
        connector.connectPipe(pipeWithDiameterMm(100));
        connector.connectPipe(pipeWithDiameterMm(100));

        assertThatThrownBy(() -> connector.connectPipe(pipeWithDiameterMm(100)))
                .isInstanceOf(BusinessRuleViolationException.class)
                .hasMessageContaining("maximum number of pipes");
    }

    @Test
    void connectPipe_allowsThree_forTee_thenRejectsFourth() {
        var connector = Connector.create(ConnectorType.TEE, 100, PipeMaterial.PVC, geoPoint());
        connector.connectPipe(pipeWithDiameterMm(100));
        connector.connectPipe(pipeWithDiameterMm(100));
        connector.connectPipe(pipeWithDiameterMm(100));

        assertThatThrownBy(() -> connector.connectPipe(pipeWithDiameterMm(100)))
                .isInstanceOf(BusinessRuleViolationException.class)
                .hasMessageContaining("maximum number of pipes");
    }

    @Test
    void getConnectedPipeIds_isUnmodifiable() {
        var connector = Connector.create(ConnectorType.ELBOW, 100, PipeMaterial.PVC, geoPoint());
        connector.connectPipe(pipeWithDiameterMm(100));

        assertThatThrownBy(() -> connector.getConnectedPipeIds().add(UUID.randomUUID()))
                .isInstanceOf(UnsupportedOperationException.class);
    }
}
