package com.waterconnect.domain.model.aggregate;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import com.waterconnect.domain.model.enums.WorkOrderStatus;
import com.waterconnect.domain.model.enums.WorkOrderType;
import com.waterconnect.domain.model.valueobject.WorkNote;

/**
 * WorkOrder Aggregate Root (Level 2-3).
 *
 * TODO: Implement in Level 2-3
 * - Factory: WorkOrder.create(type, servicePointId)
 * - State machine: CREATED → SCHEDULED → IN_PROGRESS → COMPLETED
 * - CREATED → CANCELLED (only if not yet IN_PROGRESS)
 * - Method: schedule(LocalDate date, String team)
 * - Method: start() — only if SCHEDULED
 * - Method: complete() — only if IN_PROGRESS, sets completedAt
 * - Method: cancel() — only if CREATED or SCHEDULED
 * - Method: addNote(WorkNote note)
 */
public class WorkOrder {

    private UUID workOrderId;
    private WorkOrderType type;
    private UUID servicePointId;
    private String assignedTeam;
    private LocalDate scheduledDate;
    private WorkOrderStatus status;
    private List<WorkNote> notes = new ArrayList<>();
    private Instant createdAt;
    private Instant completedAt;

    protected WorkOrder() {}

    public List<WorkNote> getNotes() {
        return Collections.unmodifiableList(notes);
    }

    // TODO: Implement in Level 2-3
}
