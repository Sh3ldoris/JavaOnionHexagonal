package com.waterconnect.domain.model.valueobject;

import java.time.Instant;
import java.util.UUID;

public record WorkNote(UUID noteId, String content, String author, Instant createdAt) {
    public WorkNote(
            String content, String author, Instant createdAt
    ) {
        if (content == null || content.isBlank()) {
            throw new IllegalArgumentException("Work note content must not be empty");
        }

        this(UUID.randomUUID(), content, author, createdAt);
    }
}
