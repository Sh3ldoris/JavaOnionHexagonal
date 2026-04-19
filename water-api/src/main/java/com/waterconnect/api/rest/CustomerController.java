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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.waterconnect.api.dto.RegisterCustomerRequestDto;
import com.waterconnect.application.command.RegisterCustomerCommand;
import com.waterconnect.application.dto.CustomerResultDto;
import com.waterconnect.application.dto.Page;
import com.waterconnect.application.query.CustomerQuery;
import com.waterconnect.application.usecase.RegisterCustomerUseCase;

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
@Tag(name = "Customers", description = "Customer registration and lookup.")
@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

    private final RegisterCustomerUseCase registerCustomerUseCase;
    private final CustomerQuery getCustomerQuery;

    public CustomerController(
            RegisterCustomerUseCase registerCustomerUseCase,
            CustomerQuery getCustomerQuery
    ) {
        this.registerCustomerUseCase = registerCustomerUseCase;
        this.getCustomerQuery = getCustomerQuery;
    }

    @Operation(summary = "Register a new customer")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Customer registered; response body is the new customer id.",
                    headers = @Header(
                            name = "Location",
                            description = "URI of the created customer resource",
                            schema = @Schema(type = "string", example = "/api/v1/customers/550e8400-e29b-41d4-a716-446655440000")),
                    content = @Content(schema = @Schema(implementation = UUID.class))),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid request (validation or bad input).",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
    })
    @PostMapping
    public ResponseEntity<UUID> registerCustomer(@Valid @RequestBody RegisterCustomerRequestDto request) {
        var command = new RegisterCustomerCommand(
                request.fullName(),
                request.customerType().name(),
                request.email(),
                request.phone(),
                request.street(),
                request.city(),
                request.postalCode(),
                request.country()
        );

        UUID customerId = this.registerCustomerUseCase.execute(command);

        return ResponseEntity
                .created(URI.create("/api/v1/customers/" + customerId))
                .body(customerId);
    }

    @Operation(summary = "Get customer by ID")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Customer found",
                    content = @Content(schema = @Schema(implementation = CustomerResultDto.class))),
            @ApiResponse(
                    responseCode = "404",
                    description = "Customer not found",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @GetMapping("/{customerId}")
    public CustomerResultDto getCustomer(
            @Parameter(description = "Customer id", required = true) @PathVariable UUID customerId
    ) {
        return this.getCustomerQuery.getById(customerId);
    }

    @Operation(summary = "List customers (paginated)")
    @ApiResponse(responseCode = "200", description = "Paginated list of customers")
    @GetMapping
    public Page<CustomerResultDto> listCustomers(
            @Parameter(description = "Zero-based page index") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "20") int size
    ) {
        return this.getCustomerQuery.getList(page, size);
    }
}
