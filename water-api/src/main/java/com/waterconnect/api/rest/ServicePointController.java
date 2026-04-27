package com.waterconnect.api.rest;

import java.net.URI;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.waterconnect.api.dto.RecordMeterReadingRequestDto;
import com.waterconnect.api.dto.RequestServiceConnectionRequestDto;
import com.waterconnect.application.dto.ServicePointResultDto;
import com.waterconnect.application.query.ServicePointQuery;
import com.waterconnect.application.usecase.ApproveServicePointConnectionUseCase;
import com.waterconnect.application.usecase.RecordMeterReadingUseCase;
import com.waterconnect.application.usecase.RequestServicePointConnectionUseCase;
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
 * REST controller for Customer operations.
 */
@Tag(name = "Service Points", description = "Service points registration and lookup.")
@RestController
@RequestMapping("/api/v1/service-points")
public class ServicePointController {

    private final RequestServicePointConnectionUseCase requestServicePointConnectionUseCase;
    private final RecordMeterReadingUseCase recordMeterReadingUseCase;
    private final ApproveServicePointConnectionUseCase approveServicePointConnectionUseCase;
    private final ServicePointQuery getServicePointQuery;

    public ServicePointController(
            RequestServicePointConnectionUseCase requestServicePointConnectionUseCase,
            RecordMeterReadingUseCase recordMeterReadingUseCase,
            ApproveServicePointConnectionUseCase approveServicePointConnectionUseCase,
            ServicePointQuery getServicePointQuery
    ) {
        this.requestServicePointConnectionUseCase = requestServicePointConnectionUseCase;
        this.recordMeterReadingUseCase = recordMeterReadingUseCase;
        this.approveServicePointConnectionUseCase = approveServicePointConnectionUseCase;
        this.getServicePointQuery = getServicePointQuery;
    }

    @Operation(summary = "Request service point connection")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Connection requested; response body is the new connection id.",
                    headers = @Header(
                            name = "Location",
                            description = "URI of the created service point connection",
                            schema = @Schema(type = "string", example = "/api/v1/service-points/550e8400-e29b-41d4-a716-446655440000")),
                    content = @Content(schema = @Schema(implementation = UUID.class))),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid request (validation or bad input).",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
    })
    @PostMapping
    public ResponseEntity<UUID> requestConnection(@Valid @RequestBody RequestServiceConnectionRequestDto request) {
        UUID connectionId = this.requestServicePointConnectionUseCase.execute(
                request.customerId(),
                request.connectorId()
        );

        return ResponseEntity
                .created(URI.create("/api/v1/service-points/" + connectionId))
                .body(connectionId);
    }

    @Operation(summary = "Get service point by ID")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Service point found",
                    content = @Content(schema = @Schema(implementation = ServicePointResultDto.class))),
            @ApiResponse(
                    responseCode = "404",
                    description = "Service point not found",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @GetMapping("/{servicePointId}")
    public ServicePointResultDto getConnection(
            @Parameter(description = "Service point id", required = true) @PathVariable UUID servicePointId
    ) {
        return this.getServicePointQuery.getById(servicePointId);
    }

    @Operation(summary = "Approve a service point connection")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Service point connection approved.",
                    content = @Content(schema = @Schema(implementation = ServicePointResultDto.class))),
    })
    @PutMapping("/{servicePointId}/approve")
    public void approve(
            @Parameter(description = "Service point id", required = true) @PathVariable UUID servicePointId
    ) {
        this.approveServicePointConnectionUseCase.execute(servicePointId);
    }

    @Operation(summary = "Activate a service point connection")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "501",
                    description = "Not yet implemented.",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @PutMapping("/{servicePointId}/activate")
    public ResponseEntity<ProblemDetail> activate(
            @Parameter(description = "Service point id", required = true) @PathVariable UUID servicePointId
    ) {
        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.NOT_IMPLEMENTED);
        problem.setTitle("Not Implemented");
        problem.setDetail("Activation of service points is not yet supported.");
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(problem);
    }

    @Operation(summary = "Record a meter reading for a service point")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Meter reading recorded.",
                    content = @Content(schema = @Schema(implementation = ServicePointResultDto.class))),
    })
    @PostMapping("/{servicePointId}/meter-readings")
    public void recordMeterReading(
            @Parameter(description = "Service point id", required = true) @PathVariable UUID servicePointId,
            @Valid @RequestBody RecordMeterReadingRequestDto request
    ) {
        this.recordMeterReadingUseCase.execute(servicePointId, request.readingM3());
    }
}
