package com.waterconnect.api.rest;

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

import com.waterconnect.api.dto.CreateWorkOrderRequestDto;
import com.waterconnect.api.dto.ScheduleWorkOrderRequest;
import com.waterconnect.application.command.ScheduleWorkOrderCommand;
import com.waterconnect.application.dto.WorkOrderResultDto;
import com.waterconnect.application.query.WorkOrderQuery;
import com.waterconnect.application.usecase.workorder.CancelWorkOrderUseCase;
import com.waterconnect.application.usecase.workorder.CompleteWorkOrderUseCase;
import com.waterconnect.application.usecase.workorder.ScheduleWorkOrderUseCase;
import com.waterconnect.application.usecase.workorder.StartWorkOrderUseCase;
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
 * REST controller for WorkOrder operations.
 */
@Tag(name = "WorkOrders", description = "WorkOrder management and lookup.")
@RestController
@RequestMapping("/api/v1/work-orders")
public class WorkOrderController {

    private final CancelWorkOrderUseCase cancelWorkOrderUseCase;
    private final CompleteWorkOrderUseCase completeWorkOrderUseCase;
    private final ScheduleWorkOrderUseCase scheduleWorkOrderUseCase;
    private final StartWorkOrderUseCase startWorkOrderUseCase;
    private final WorkOrderQuery workOrderQuery;

    public WorkOrderController(
            CancelWorkOrderUseCase cancelWorkOrderUseCase,
            CompleteWorkOrderUseCase completeWorkOrderUseCase,
            ScheduleWorkOrderUseCase scheduleWorkOrderUseCase,
            StartWorkOrderUseCase startWorkOrderUseCase,
            WorkOrderQuery workOrderQuery
    ) {
        this.cancelWorkOrderUseCase = cancelWorkOrderUseCase;
        this.completeWorkOrderUseCase = completeWorkOrderUseCase;
        this.scheduleWorkOrderUseCase = scheduleWorkOrderUseCase;
        this.startWorkOrderUseCase = startWorkOrderUseCase;
        this.workOrderQuery = workOrderQuery;
    }

    @Operation(summary = "Create a new work order")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Work order created; response body is the new work order id.",
                    headers = @Header(
                            name = "Location",
                            description = "URI of the created work order resource",
                            schema = @Schema(type = "string", example = "/api/v1/work-orders/550e8400-e29b-41d4-a716-446655440000")),
                    content = @Content(schema = @Schema(implementation = UUID.class))),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid request (validation or bad input).",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
    })
    @PostMapping
    public ResponseEntity<UUID> createWorkOrder(@Valid @RequestBody CreateWorkOrderRequestDto request) {
        // TODO: Implement
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

    @Operation(summary = "Get by ID")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Work order found",
                    content = @Content(schema = @Schema(implementation = WorkOrderResultDto.class))),
            @ApiResponse(
                    responseCode = "404",
                    description = "Work order not found",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @GetMapping("/{workOrderId}")
    public WorkOrderResultDto getWorkOrderById(
            @Parameter(description = "Work order id", required = true) @PathVariable UUID workOrderId
    ) {
        return this.workOrderQuery.getById(workOrderId);
    }

    @Operation(summary = "Schedule a work order")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Work order scheduled."),
    })
    @PutMapping("/{workOrderId}/schedule")
    public void scheduleWorkOrder(
            @Parameter(description = "Work order id", required = true) @PathVariable UUID workOrderId,
            @Valid @RequestBody ScheduleWorkOrderRequest request
    ) {
        this.scheduleWorkOrderUseCase.execute(new ScheduleWorkOrderCommand(
                workOrderId, request.scheduledDate(), request.assignedTeam()
        ));
    }

    // TODO: Start, complete and cancel
}
