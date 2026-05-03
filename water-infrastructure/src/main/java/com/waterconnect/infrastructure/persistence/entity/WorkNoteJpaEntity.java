package com.waterconnect.infrastructure.persistence.entity;

import com.waterconnect.domain.model.valueobject.WorkNote;
import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "work_notes")
public class WorkNoteJpaEntity {

    @Id
    @Column(updatable = false, nullable = false)
    private UUID noteId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workOrderId", nullable = false)
    private WorkOrderJpaEntity workOrder;

    @Column(nullable = false)
    private String content;

    private String author;

    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    protected WorkNoteJpaEntity() {}

    public WorkNote toDomain() {
        return new WorkNote(noteId, content, author, createdAt);
    }

    public static WorkNoteJpaEntity fromDomain(WorkNote note) {
        var entity = new WorkNoteJpaEntity();
        entity.setNoteId(note.noteId());
        entity.setContent(note.content());
        entity.setAuthor(note.author());
        entity.setCreatedAt(note.createdAt());
        return entity;
    }

    public UUID getNoteId() { return noteId; }
    public String getContent() { return content; }
    public String getAuthor() { return author; }
    public Instant getCreatedAt() { return createdAt; }

    public WorkOrderJpaEntity getWorkOrder() {
        return workOrder;
    }

    public void setWorkOrder(WorkOrderJpaEntity workOrder) {
        this.workOrder = workOrder;
    }

    public void setNoteId(UUID noteId) {
        this.noteId = noteId;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}
