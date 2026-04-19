package com.waterconnect.api.dto;

import com.waterconnect.api.dto.enums.CustomerType;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Payload to register a new customer.")
public record RegisterCustomerRequestDto(
        @Schema(description = "Legal or display name", example = "Jane Doe")
        @NotBlank String fullName,
        @Schema(description = "Residential or commercial classification")
        @NotNull CustomerType customerType,
        @Schema(description = "Contact email", example = "jane@example.com")
        @NotBlank @Email String email,
        @Schema(description = "Phone number", example = "+1-555-0100")
        @NotBlank String phone,
        @Schema(description = "Street address line", example = "123 River Rd")
        @NotBlank String street,
        @Schema(description = "City", example = "Springfield")
        @NotBlank String city,
        @Schema(description = "Postal or ZIP code", example = "62701")
        @NotBlank String postalCode,
        @Schema(description = "Country", example = "US")
        @NotBlank String country
) {
}
