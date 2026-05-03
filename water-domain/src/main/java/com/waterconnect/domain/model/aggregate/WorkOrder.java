package com.waterconnect.domain.model.aggregate;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import com.waterconnect.domain.event.DomainEvent;
import com.waterconnect.domain.event.WorkOrderCompletedEvent;
import com.waterconnect.domain.event.WorkOrderScheduledEvent;
import com.waterconnect.domain.exception.BusinessRuleViolationException;
import com.waterconnect.domain.model.enums.WorkOrderStatus;
import com.waterconnect.domain.model.enums.WorkOrderType;
import com.waterconnect.domain.model.valueobject.WorkNote;

/**
 * WorkOrder Aggregate Root.
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

    private final List<DomainEvent> events = new ArrayList<>();

    protected WorkOrder() {}

    public List<WorkNote> getNotes() {
        return Collections.unmodifiableList(notes);
    }

    public static WorkOrder create(WorkOrderType type, UUID servicePointId) {
        Objects.requireNonNull(type, "WorkOrderType must not be null");
        Objects.requireNonNull(servicePointId, "ServicePointId must not be null");

        // Create a new domain object
        var  workOrder = new WorkOrder();
        // Set attributes from parameters
        workOrder.type = type;
        workOrder.servicePointId = servicePointId;
        // Set default attributes
        workOrder.workOrderId = UUID.randomUUID();
        workOrder.status = WorkOrderStatus.CREATED;
        workOrder.createdAt = Instant.now();

        return workOrder;
    }

    public void schedule(LocalDate date, String team) {
        Objects.requireNonNull(date, "date must not be null");
        Objects.requireNonNull(team, "team must not be null");

        if (team.isBlank()) {
            throw new BusinessRuleViolationException("Team must not be blank");
        }
        if (date.isBefore(LocalDate.now())) {
            throw new BusinessRuleViolationException("Work order must be scheduled in the future");
        }

        this.scheduledDate = date;
        this.assignedTeam = team;
        this.status = WorkOrderStatus.SCHEDULED;

        // Log the schedule
        var noteComment = String.format("Work order scheduled to %s and assigned to %s", this.scheduledDate, this.assignedTeam);
        this.addNote(new WorkNote(noteComment, "system", Instant.now()));

        // Create domain event
        this.events.add(new WorkOrderScheduledEvent(this.workOrderId));
    }

    public void start() {
        if (this.status != WorkOrderStatus.SCHEDULED) {
            throw new BusinessRuleViolationException("WorkOrder must be scheduled. Current status is " + this.status);
        }

        this.status = WorkOrderStatus.IN_PROGRESS;

        // Log the state change
        this.addNote(new WorkNote("Work order started", "system", Instant.now()));
    }

    public void complete() {
        if (this.status != WorkOrderStatus.IN_PROGRESS) {
            throw new BusinessRuleViolationException("WorkOrder must be in progress. Current status is " + this.status);
        }

        this.status = WorkOrderStatus.COMPLETED;
        this.completedAt = Instant.now();

        // Log the state change
        this.addNote(new WorkNote("Work order completed", "system", Instant.now()));

        // Create domain event
        this.events.add(new WorkOrderCompletedEvent(this.workOrderId));
    }

    public void cancel() {
        if (!Set.of(WorkOrderStatus.CREATED, WorkOrderStatus.SCHEDULED).contains(this.status)) {
            throw new BusinessRuleViolationException("WorkOrder must be in the CREATED or SCHEDULED status. Current status is " + this.status);
        }

        this.status = WorkOrderStatus.CANCELLED;

        // Log the state change
        this.addNote(new WorkNote("Work order canceled", "system", Instant.now()));
    }

    public void addNote(WorkNote note) {
        Objects.requireNonNull(note, "WorkNote must not be null");

        this.notes.add(note);
    }

    public void cleanEvents() {
        this.events.clear();
    }

    public List<DomainEvent> getEvents() {
        return Collections.unmodifiableList(events);
    }

    public UUID getWorkOrderId() {
        return workOrderId;
    }

    public WorkOrderType getType() {
        return type;
    }

    public UUID getServicePointId() {
        return servicePointId;
    }

    public String getAssignedTeam() {
        return assignedTeam;
    }

    public LocalDate getScheduledDate() {
        return scheduledDate;
    }

    public WorkOrderStatus getStatus() {
        return status;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getCompletedAt() {
        return completedAt;
    }

    protected void setWorkOrderId(UUID workOrderId) {
        this.workOrderId = workOrderId;
    }

    protected void setType(WorkOrderType type) {
        this.type = type;
    }

    protected void setServicePointId(UUID servicePointId) {
        this.servicePointId = servicePointId;
    }

    protected void setAssignedTeam(String assignedTeam) {
        this.assignedTeam = assignedTeam;
    }

    protected void setScheduledDate(LocalDate scheduledDate) {
        this.scheduledDate = scheduledDate;
    }

    protected void setStatus(WorkOrderStatus status) {
        this.status = status;
    }

    protected void setNotes(List<WorkNote> notes) {
        this.notes = notes;
    }

    protected void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    protected void setCompletedAt(Instant completedAt) {
        this.completedAt = completedAt;
    }
}
