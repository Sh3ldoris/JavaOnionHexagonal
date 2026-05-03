package com.waterconnect.infrastructure.persistence.entity.factory;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import com.waterconnect.domain.model.aggregate.WorkOrder;
import com.waterconnect.domain.model.enums.WorkOrderStatus;
import com.waterconnect.domain.model.enums.WorkOrderType;
import com.waterconnect.domain.model.valueobject.WorkNote;

public class WorkOrderFactory extends WorkOrder {

    private WorkOrderFactory() {
    }

    public static WorkOrder create(
            UUID workOrderId,
            WorkOrderType type,
            UUID servicePointId,
            String assignedTeam,
            LocalDate scheduledDate,
            WorkOrderStatus status,
            List<WorkNote> notes,
            Instant createdAt,
            Instant completedAt
    ) {
        var wo = new WorkOrderFactory();
        wo.setWorkOrderId(workOrderId);
        wo.setType(type);
        wo.setServicePointId(servicePointId);
        wo.setAssignedTeam(assignedTeam);
        wo.setScheduledDate(scheduledDate);
        wo.setStatus(status);
        wo.setNotes(notes);
        wo.setCreatedAt(createdAt);
        wo.setCompletedAt(completedAt);

        return wo;
    }
}
