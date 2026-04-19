package com.waterconnect.application.query;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.waterconnect.application.dto.PipeResultDto;
import com.waterconnect.application.dto.enums.PipeMaterial;
import com.waterconnect.application.dto.enums.PipeStatus;
import com.waterconnect.application.dto.mapper.PipeResultDtoMapper;
import com.waterconnect.domain.exception.EntityNotFoundException;
import com.waterconnect.domain.port.outbound.PipeRepository;

@Service
public class PipeQuery {

    private final PipeRepository pipeRepository;

    public PipeQuery(PipeRepository pipeRepository) {
        this.pipeRepository = pipeRepository;
    }

    public PipeResultDto getById(UUID pipeId) throws EntityNotFoundException {
        var pipeOptional = pipeRepository.findById(pipeId);

        if (pipeOptional.isEmpty()) {
            throw new EntityNotFoundException("Pipe", pipeId);
        }

        return PipeResultDtoMapper.fromDomain(pipeOptional.get());
    }

    public List<PipeResultDto> getList(PipeMaterial material, PipeStatus status) {
        return this.pipeRepository.findAll(material.name(), status.name())
                .stream()
                .map(PipeResultDtoMapper::fromDomain)
                .toList();
    }
}
