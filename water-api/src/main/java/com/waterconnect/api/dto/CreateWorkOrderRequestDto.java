package com.waterconnect.api.dto;

import java.util.UUID;

import com.waterconnect.api.dto.enums.WorkOrderType;
import jakarta.validation.constraints.NotNull;

public record CreateWorkOrderRequestDto(
        @NotNull WorkOrderType type,
        @NotNull UUID servicePointId,
        String assignedTeam
) {
}
