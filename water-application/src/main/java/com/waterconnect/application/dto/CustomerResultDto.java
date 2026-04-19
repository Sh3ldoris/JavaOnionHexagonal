package com.waterconnect.application.dto;

import java.time.Instant;
import java.util.UUID;

import com.waterconnect.application.dto.enums.CustomerType;

public record CustomerResultDto(
        UUID customerId,
        String fullName,
        CustomerType customerType,
        String email,
        String phone,
        AddressResultDto address,
        Instant registeredAt
) {
}
