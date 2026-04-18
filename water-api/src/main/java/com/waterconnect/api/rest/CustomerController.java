package com.waterconnect.api.rest;

import java.net.URI;
import java.util.UUID;

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
import jakarta.validation.Valid;

/**
 * REST controller for Customer operations.
 */
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

    @GetMapping("/{customerId}")
    public CustomerResultDto getCustomer(@PathVariable UUID customerId) {
        return this.getCustomerQuery.getById(customerId);
    }

    @GetMapping
    public Page<CustomerResultDto> listCustomers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        return this.getCustomerQuery.getList(page, size);
    }
}
