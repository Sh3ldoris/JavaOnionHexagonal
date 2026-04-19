package com.waterconnect.application.dto.mapper;

import com.waterconnect.application.dto.PipeResultDto;
import com.waterconnect.application.dto.enums.PipeMaterial;
import com.waterconnect.application.dto.enums.PipeStatus;
import com.waterconnect.domain.model.aggregate.Pipe;

public class PipeResultDtoMapper {

    private PipeResultDtoMapper() {}

    public static PipeResultDto fromDomain(Pipe pipe) {
        var location = GeoSegmentDtoMapper.fromDomain(pipe.getLocation());

        return new PipeResultDto(
                pipe.getPipeId(),
                PipeMaterial.fromString(pipe.getMaterial().name()),
                pipe.getDiameterMm(),
                pipe.getLengthMeters(),
                pipe.getPressureRatingBar(),
                PipeStatus.fromString(pipe.getStatus().name()),
                pipe.getInstalledAt(),
                location
        );
    }
}
