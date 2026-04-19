package com.waterconnect.api.rest;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.waterconnect.api.dto.CreatePipeRequestDto;
import com.waterconnect.application.command.CreatePipeCommand;
import com.waterconnect.application.dto.GeoPointDto;
import com.waterconnect.application.dto.GeoSegmentDto;
import com.waterconnect.application.dto.PipeResultDto;
import com.waterconnect.application.dto.enums.PipeMaterial;
import com.waterconnect.application.dto.enums.PipeStatus;
import com.waterconnect.application.query.PipeQuery;
import com.waterconnect.application.usecase.CreatePipeUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

/**
 * REST controller for Pipe operations.
 */
@Tag(name = "Pipes", description = "Pipe registration and lookup.")
@RestController
@RequestMapping("/api/v1/pipes")
public class PipeController {

    private final CreatePipeUseCase createPipeUseCase;
    private final PipeQuery pipeQuery;

    public PipeController(CreatePipeUseCase createPipeUseCase, PipeQuery pipeQuery) {
        this.createPipeUseCase = createPipeUseCase;
        this.pipeQuery = pipeQuery;
    }

    @Operation(summary = "Create a new pipe")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Pipe created; response body is the new pipe id.",
                    headers = @Header(
                            name = "Location",
                            description = "URI of the created pipe resource",
                            schema = @Schema(type = "string", example = "/api/v1/pipes/550e8400-e29b-41d4-a716-446655440000")),
                    content = @Content(schema = @Schema(implementation = UUID.class))),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid request (validation or bad input).",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
    })
    @PostMapping
    public ResponseEntity<UUID> createPipe(@Valid @RequestBody CreatePipeRequestDto request) {
        var startPoint = new GeoPointDto(request.startLatitude(), request.startLongitude());
        var endPoint = new GeoPointDto(request.endLatitude(), request.endLongitude());
        var location = new GeoSegmentDto(startPoint, endPoint);
        var command = new CreatePipeCommand(
                request.diameterMm(),
                request.lengthMeters(),
                request.pressureRatingBar(),
                request.material().name(),
                location
        );

        UUID pipeId = this.createPipeUseCase.execute(command);

        return ResponseEntity
                .created(URI.create("/api/v1/pipes/" + pipeId))
                .body(pipeId);
    }

    @Operation(summary = "Get pipe by ID")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Pipe found",
                    content = @Content(schema = @Schema(implementation = PipeResultDto.class))),
            @ApiResponse(
                    responseCode = "404",
                    description = "Customer not found",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @GetMapping("/{pipeId}")
    public PipeResultDto getPipe(
            @Parameter(description = "Pipe id", required = true) @PathVariable UUID pipeId
    ) {
        return this.pipeQuery.getById(pipeId);
    }

    @Operation(summary = "List pipes")
    @ApiResponse(responseCode = "200", description = "List of pipes")
    @GetMapping
    public List<PipeResultDto> listPipes(
            @Parameter(description = "Pipe material") PipeMaterial material,
            @Parameter(description = "Page status") PipeStatus status
            ) {
        return this.pipeQuery.getList(material, status);
    }
}
