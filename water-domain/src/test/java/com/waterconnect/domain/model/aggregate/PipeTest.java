package com.waterconnect.domain.model.aggregate;

import static com.waterconnect.domain.model.aggregate.support.AggregateTestFixtures.geoSegment;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.Instant;

import org.junit.jupiter.api.Test;

import com.waterconnect.domain.exception.BusinessRuleViolationException;
import com.waterconnect.domain.model.enums.PipeMaterial;
import com.waterconnect.domain.model.enums.PipeStatus;

class PipeTest {

    @Test
    void planNew_setsPlannedStateAndFields() {
        var segment = geoSegment();
        var pipe = Pipe.planNew(100, 25.5, 10.0, PipeMaterial.COPPER, segment);

        assertThat(pipe.getPipeId()).isNotNull();
        assertThat(pipe.getStatus()).isEqualTo(PipeStatus.PLANNED);
        assertThat(pipe.getDiameterMm()).isEqualTo(100);
        assertThat(pipe.getLengthMeters()).isEqualTo(25.5);
        assertThat(pipe.getPressureRatingBar()).isEqualTo(10.0);
        assertThat(pipe.getMaterial()).isEqualTo(PipeMaterial.COPPER);
        assertThat(pipe.getLocation()).isEqualTo(segment);
        assertThat(pipe.getInstalledAt()).isNull();
    }

    @Test
    void planNew_acceptsBoundaryDiameters() {
        var pMin = Pipe.planNew(15, 1.0, 1.0, PipeMaterial.PVC, geoSegment());
        var pMax = Pipe.planNew(1200, 1.0, 1.0, PipeMaterial.PVC, geoSegment());
        assertThat(pMin.getDiameterMm()).isEqualTo(15);
        assertThat(pMax.getDiameterMm()).isEqualTo(1200);
    }

    @Test
    void planNew_rejectsDiameterBelowRange() {
        assertThatThrownBy(() -> Pipe.planNew(14, 1.0, 1.0, PipeMaterial.PVC, geoSegment()))
                .isInstanceOf(BusinessRuleViolationException.class)
                .hasMessageContaining("diameterMm");
    }

    @Test
    void planNew_rejectsDiameterAboveRange() {
        assertThatThrownBy(() -> Pipe.planNew(1201, 1.0, 1.0, PipeMaterial.PVC, geoSegment()))
                .isInstanceOf(BusinessRuleViolationException.class)
                .hasMessageContaining("diameterMm");
    }

    @Test
    void planNew_rejectsNonPositivePressure() {
        assertThatThrownBy(() -> Pipe.planNew(100, 1.0, 0.0, PipeMaterial.PVC, geoSegment()))
                .isInstanceOf(BusinessRuleViolationException.class)
                .hasMessageContaining("pressureRatingBar");
        assertThatThrownBy(() -> Pipe.planNew(100, 1.0, -1.0, PipeMaterial.PVC, geoSegment()))
                .isInstanceOf(BusinessRuleViolationException.class)
                .hasMessageContaining("pressureRatingBar");
    }

    @Test
    void planNew_rejectsNullMaterial() {
        assertThatThrownBy(() -> Pipe.planNew(100, 1.0, 1.0, null, geoSegment()))
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("pipeMaterial");
    }

    @Test
    void planNew_rejectsNullLocation() {
        assertThatThrownBy(() -> Pipe.planNew(100, 1.0, 1.0, PipeMaterial.PVC, null))
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("location");
    }

    @Test
    void planNew_acceptsNonPositiveLengthMeters_currentApi() {
        var pipe = Pipe.planNew(100, -3.0, 1.0, PipeMaterial.HDPE, geoSegment());
        assertThat(pipe.getLengthMeters()).isEqualTo(-3.0);
    }

    @Test
    void install_transitionsFromPlannedToInstalled_andSetsInstalledAt() {
        var pipe = Pipe.planNew(50, 2.0, 5.0, PipeMaterial.STEEL, geoSegment());
        Instant before = Instant.now();

        pipe.install();

        Instant after = Instant.now();
        assertThat(pipe.getStatus()).isEqualTo(PipeStatus.INSTALLED);
        assertThat(pipe.getInstalledAt()).isNotNull();
        assertThat(pipe.getInstalledAt()).isAfterOrEqualTo(before);
        assertThat(pipe.getInstalledAt()).isBeforeOrEqualTo(after);
    }

    @Test
    void install_failsWhenNotPlanned() {
        var pipe = Pipe.planNew(50, 2.0, 5.0, PipeMaterial.STEEL, geoSegment());
        pipe.install();

        assertThatThrownBy(pipe::install)
                .isInstanceOf(BusinessRuleViolationException.class)
                .hasMessageContaining("PLANNED");
    }

    @Test
    void activate_transitionsFromInstalledToActive() {
        var pipe = Pipe.planNew(50, 2.0, 5.0, PipeMaterial.STEEL, geoSegment());
        pipe.install();
        pipe.activate();
        assertThat(pipe.getStatus()).isEqualTo(PipeStatus.ACTIVE);
    }

    @Test
    void activate_failsWhenNotInstalled() {
        var planned = Pipe.planNew(50, 2.0, 5.0, PipeMaterial.STEEL, geoSegment());
        assertThatThrownBy(planned::activate)
                .isInstanceOf(BusinessRuleViolationException.class)
                .hasMessageContaining("INSTALLED");

        var pipe = Pipe.planNew(50, 2.0, 5.0, PipeMaterial.STEEL, geoSegment());
        pipe.install();
        pipe.activate();
        assertThatThrownBy(pipe::activate)
                .isInstanceOf(BusinessRuleViolationException.class)
                .hasMessageContaining("INSTALLED");
    }

    @Test
    void decommission_transitionsFromActiveToDecommissioned() {
        var pipe = Pipe.planNew(50, 2.0, 5.0, PipeMaterial.STEEL, geoSegment());
        pipe.install();
        pipe.activate();
        pipe.decommission();
        assertThat(pipe.getStatus()).isEqualTo(PipeStatus.DECOMMISSIONED);
    }

    @Test
    void decommission_failsWhenNotActive() {
        var pipe = Pipe.planNew(50, 2.0, 5.0, PipeMaterial.STEEL, geoSegment());
        assertThatThrownBy(pipe::decommission)
                .isInstanceOf(BusinessRuleViolationException.class)
                .hasMessageContaining("ACTIVE");
    }
}
