package com.waterconnect.infrastructure.persistence.entity;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import com.waterconnect.domain.model.aggregate.WorkOrder;
import com.waterconnect.domain.model.enums.WorkOrderStatus;
import com.waterconnect.domain.model.enums.WorkOrderType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "work_order")
public class WorkOrderJpaEntity {

    @Id
    private UUID workOrderId;

    @Enumerated(EnumType.STRING)
    private WorkOrderType type;
    private UUID servicePointId;
    private String assignedTeam;
    private LocalDate scheduledDate;

    @Enumerated(EnumType.STRING)
    private WorkOrderStatus status;

    @OneToMany(mappedBy = "workOrder", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<WorkNoteJpaEntity> notes;

    private Instant createdAt;
    private Instant completedAt;

    protected WorkOrderJpaEntity() {}

    public static WorkOrderJpaEntity fromDomain(WorkOrder domain) {
        var  entity = new WorkOrderJpaEntity();
        entity.workOrderId = domain.getWorkOrderId();
        entity.type = domain.getType();
        entity.servicePointId = domain.getServicePointId();
        entity.assignedTeam = domain.getAssignedTeam();
        entity.scheduledDate = domain.getScheduledDate();
        entity.status = domain.getStatus();

        entity.notes = domain
                .getNotes().stream()
                .map(WorkNoteJpaEntity::fromDomain)
                .toList();

        entity.createdAt =domain.getCreatedAt();
        entity.completedAt = domain.getCompletedAt();

        return entity;
    }

    public UUID getWorkOrderId() {
        return workOrderId;
    }

    public void setWorkOrderId(UUID workOrderId) {
        this.workOrderId = workOrderId;
    }

    public WorkOrderType getType() {
        return type;
    }

    public void setType(WorkOrderType type) {
        this.type = type;
    }

    public UUID getServicePointId() {
        return servicePointId;
    }

    public void setServicePointId(UUID servicePointId) {
        this.servicePointId = servicePointId;
    }

    public String getAssignedTeam() {
        return assignedTeam;
    }

    public void setAssignedTeam(String assignedTeam) {
        this.assignedTeam = assignedTeam;
    }

    public LocalDate getScheduledDate() {
        return scheduledDate;
    }

    public void setScheduledDate(LocalDate scheduledDate) {
        this.scheduledDate = scheduledDate;
    }

    public WorkOrderStatus getStatus() {
        return status;
    }

    public void setStatus(WorkOrderStatus status) {
        this.status = status;
    }

    public List<WorkNoteJpaEntity> getNotes() {
        return notes;
    }

    public void setNotes(List<WorkNoteJpaEntity> notes) {
        this.notes = notes;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(Instant completedAt) {
        this.completedAt = completedAt;
    }
}
