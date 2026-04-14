package com.waterconnect.api.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.waterconnect.application.usecase.RegisterCustomerUseCase;

/**
 * REST controller for Customer operations.
 *
 * TODO: Implement endpoints from openapi.yaml
 * - POST /api/v1/customers — register customer
 * - GET  /api/v1/customers/{id} — get by ID
 * - GET  /api/v1/customers — list paginated
 *
 * Map between API DTOs and use case commands.
 * Return proper HTTP status codes (201 for create, 404 for not found, etc.)
 */
@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

    private final RegisterCustomerUseCase registerCustomerUseCase;

    public CustomerController(RegisterCustomerUseCase registerCustomerUseCase) {
        this.registerCustomerUseCase = registerCustomerUseCase;
    }

    // TODO: Implement endpoints
}
