package com.waterconnect.api.dto;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;

public record RequestServiceConnectionRequestDto(
        @NotNull UUID customerId,
        @NotNull UUID connectorId
) {
}
