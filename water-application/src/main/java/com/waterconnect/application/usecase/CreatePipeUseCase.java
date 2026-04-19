package com.waterconnect.application.usecase;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.waterconnect.application.command.CreatePipeCommand;
import com.waterconnect.application.dto.mapper.GeoSegmentDtoMapper;
import com.waterconnect.domain.model.aggregate.Pipe;
import com.waterconnect.domain.model.enums.PipeMaterial;
import com.waterconnect.domain.model.valueobject.GeoSegment;
import com.waterconnect.domain.port.outbound.PipeRepository;

/**
 * Use case: Create a new pipe in the water network.
 */
@Service
public class CreatePipeUseCase {

    private final PipeRepository pipeRepository;

    public CreatePipeUseCase(PipeRepository pipeRepository) {
        this.pipeRepository = pipeRepository;
    }

    @Transactional
    public UUID execute(CreatePipeCommand command) {
        GeoSegment geoSegment = GeoSegmentDtoMapper.toDomain(command.location());
        // Create a new Pipe
        var pipe = Pipe.planNew(command.diameterMm(), command.lengthMeters(),
                command.pressureRatingBar(), PipeMaterial.fromString(command.pipeMaterial()), geoSegment);

        // Save the new Pipe
        this.pipeRepository.save(pipe);

        return pipe.getPipeId();
    }

}
