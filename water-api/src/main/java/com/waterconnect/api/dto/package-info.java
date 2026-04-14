/**
 * API DTOs — request/response objects for REST endpoints.
 *
 * TODO: Create DTOs matching the OpenAPI schemas:
 * - RegisterCustomerRequest
 * - CustomerResponse
 * - CreatePipeRequest / PipeResponse
 * - CreateConnectorRequest
 * - RequestServiceConnectionRequest
 * - etc.
 *
 * Use Java records for immutability. Add Jakarta validation annotations.
 * Example:
 *
 *   public record RegisterCustomerRequest(
 *       @NotBlank String fullName,
 *       @NotNull CustomerType customerType,
 *       @Email String email,
 *       @NotBlank String phone,
 *       @NotBlank String street,
 *       @NotBlank String city,
 *       @NotBlank String postalCode,
 *       @NotBlank String country
 *   ) {}
 */
package com.waterconnect.api.dto;
