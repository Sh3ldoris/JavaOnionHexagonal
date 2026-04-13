package com.waterconnect.application.usecase;

import java.util.UUID;

import com.waterconnect.application.command.CreatePipeCommand;
import com.waterconnect.application.dto.mapper.GeoSegmentDtoMapper;
import com.waterconnect.domain.model.aggregate.Pipe;
import com.waterconnect.domain.model.enums.PipeMaterial;
import com.waterconnect.domain.model.valueobject.GeoSegment;
import com.waterconnect.domain.port.outbound.PipeRepository;

/**
 * Use case: Create a new pipe in the water network.
 */
public class CreatePipeUseCase {

    private final PipeRepository pipeRepository;

    public CreatePipeUseCase(PipeRepository pipeRepository) {
        this.pipeRepository = pipeRepository;
    }

    public UUID execute(CreatePipeCommand command) {
        GeoSegment geoSegment = GeoSegmentDtoMapper.mapToGeoPoint(command.location());
        // Create a new Pipe
        var pipe = Pipe.planNew(command.diameterMm(), command.lengthMeters(),
                command.pressureRatingBar(), PipeMaterial.fromString(command.pipeMaterial()), geoSegment);

        // Save the new Pipe
        this.pipeRepository.save(pipe);

        return pipe.getPipeId();
    }

}
