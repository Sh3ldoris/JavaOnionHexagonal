package com.waterconnect.infrastructure.persistence.entity.factory;

import java.time.Instant;
import java.util.UUID;

import com.waterconnect.domain.model.aggregate.Pipe;
import com.waterconnect.domain.model.enums.PipeMaterial;
import com.waterconnect.domain.model.enums.PipeStatus;
import com.waterconnect.domain.model.valueobject.GeoSegment;

public class PipeFactory extends Pipe {

    private PipeFactory() {}

    public static Pipe createPipe(
            UUID pipeId,
            PipeMaterial material,
            int diameterMm,
            double lengthMeters,
            double pressureRatingBar,
            PipeStatus status,
            Instant installedAt,
            GeoSegment location
    ) {
        var pipe = new PipeFactory();
        pipe.setPipeId(pipeId);
        pipe.setMaterial(material);
        pipe.setDiameterMm(diameterMm);
        pipe.setLengthMeters(lengthMeters);
        pipe.setPressureRatingBar(pressureRatingBar);
        pipe.setStatus(status);
        pipe.setInstalledAt(installedAt);
        pipe.setLocation(location);
        return pipe;
    }
}
