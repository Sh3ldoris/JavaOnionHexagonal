package com.waterconnect.api.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.waterconnect.application.usecase.CreatePipeUseCase;

import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * TODO: Implement pipe endpoints from openapi.yaml
 */
@Tag(name = "Pipes", description = "Pipe management (endpoints pending).")
@RestController
@RequestMapping("/api/v1/pipes")
public class PipeController {

    private final CreatePipeUseCase createPipeUseCase;

    public PipeController(CreatePipeUseCase createPipeUseCase) {
        this.createPipeUseCase = createPipeUseCase;
    }

    // TODO: Implement endpoints
}
