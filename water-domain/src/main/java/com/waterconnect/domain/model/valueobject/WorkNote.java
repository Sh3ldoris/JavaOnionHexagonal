package com.waterconnect.domain.model.valueobject;

import java.time.Instant;

public record WorkNote(String content, String author, Instant createdAt) {
    public WorkNote {
        if (content == null || content.isBlank()) {
            throw new IllegalArgumentException("Work note content must not be empty");
        }
    }
}