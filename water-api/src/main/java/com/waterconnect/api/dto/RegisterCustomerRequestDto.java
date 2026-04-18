package com.waterconnect.api.dto;

import com.waterconnect.api.dto.enums.CustomerType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegisterCustomerRequestDto(
        @NotBlank String fullName,
        @NotNull CustomerType customerType,
        @NotBlank @Email String email,
        @NotBlank String phone,
        @NotBlank String street,
        @NotBlank String city,
        @NotBlank String postalCode,
        @NotBlank String country
) {
}
