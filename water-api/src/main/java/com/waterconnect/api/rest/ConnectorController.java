package com.waterconnect.api.rest;

import java.net.URI;
import java.util.UUID;

import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.waterconnect.api.dto.CreateConnectorRequestDto;
import com.waterconnect.application.command.CreateConnectorCommand;
import com.waterconnect.application.dto.ConnectorResultDto;
import com.waterconnect.application.dto.GeoPointDto;
import com.waterconnect.application.query.ConnectorQuery;
import com.waterconnect.application.usecase.CreateConnectorUseCase;
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
 * REST controller for Connector operations.
 */
@Tag(name = "Connectors", description = "Connector management and lookup.")
@RestController
@RequestMapping("/api/v1/connectors")
public class ConnectorController {

    private final CreateConnectorUseCase createConnectorUseCase;
    private final ConnectorQuery connectorQuery;

    public ConnectorController(CreateConnectorUseCase createConnectorUseCase, ConnectorQuery connectorQuery) {
        this.createConnectorUseCase = createConnectorUseCase;
        this.connectorQuery = connectorQuery;
    }

    @Operation(summary = "Create a new connector")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Connector created; response body is the new connector id.",
                    headers = @Header(
                            name = "Location",
                            description = "URI of the created connector resource",
                            schema = @Schema(type = "string", example = "/api/v1/connector/550e8400-e29b-41d4-a716-446655440000")),
                    content = @Content(schema = @Schema(implementation = UUID.class))),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid request (validation or bad input).",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
    })
    @PostMapping
    public ResponseEntity<UUID> createConnector(@Valid @RequestBody CreateConnectorRequestDto request) {
        var location = new GeoPointDto(request.latitude(),  request.longitude());
        var command = new CreateConnectorCommand(
                request.connectorType().name(),
                request.diameterMm(),
                request.material().name(),
                location
        );

        UUID connectorId = this.createConnectorUseCase.execute(command);

        return ResponseEntity
                .created(URI.create("/api/v1/connectors/" + connectorId))
                .body(connectorId);
    }

    @Operation(summary = "Get connector by ID")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Connector found",
                    content = @Content(schema = @Schema(implementation = ConnectorResultDto.class))),
            @ApiResponse(
                    responseCode = "404",
                    description = "Connector not found",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @GetMapping("/{connectorId}")
    public ConnectorResultDto getConnector(
            @Parameter(description = "Connector id", required = true) @PathVariable UUID connectorId
    ) {
        return this.connectorQuery.getById(connectorId);
    }
}
