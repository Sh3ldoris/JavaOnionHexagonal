package com.waterconnect.api.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;

public record ScheduleWorkOrderRequest(
        @NotNull LocalDate scheduledDate,
        String assignedTeam
) {
}
