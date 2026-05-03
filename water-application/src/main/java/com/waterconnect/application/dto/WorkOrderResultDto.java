package com.waterconnect.application.dto;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import com.waterconnect.application.dto.enums.WorkOrderStatus;
import com.waterconnect.application.dto.enums.WorkOrderType;

public record WorkOrderResultDto(
        UUID workOrderId,
        WorkOrderType type,
        UUID servicePointId,
        String assignedTeam,
        LocalDate scheduledDate,
        WorkOrderStatus status,
        List<WorkNoteDto> notes,
        Instant createdAt,
        Instant completedAt
) {
}
