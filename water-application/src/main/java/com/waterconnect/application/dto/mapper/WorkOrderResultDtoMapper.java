package com.waterconnect.application.dto.mapper;

import com.waterconnect.application.dto.WorkOrderResultDto;
import com.waterconnect.application.dto.enums.WorkOrderStatus;
import com.waterconnect.application.dto.enums.WorkOrderType;
import com.waterconnect.domain.model.aggregate.WorkOrder;

public class WorkOrderResultDtoMapper {

    private WorkOrderResultDtoMapper() {}

    public static WorkOrderResultDto fromDomain(WorkOrder workOrder) {
        return new WorkOrderResultDto(
                workOrder.getWorkOrderId(),
                WorkOrderType.fromString(workOrder.getType().name()),
                workOrder.getServicePointId(),
                workOrder.getAssignedTeam(),
                workOrder.getScheduledDate(),
                WorkOrderStatus.fromString(workOrder.getStatus().name()),
                workOrder.getNotes().stream()
                        .map(WorkNoteDtoMapper::toDto)
                        .toList(),
                workOrder.getCreatedAt(),
                workOrder.getCompletedAt()
        );
    }
}
