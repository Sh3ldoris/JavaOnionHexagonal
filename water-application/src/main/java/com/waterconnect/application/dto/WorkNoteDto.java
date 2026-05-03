package com.waterconnect.application.dto;

import java.time.Instant;

public record WorkNoteDto(String content, String author, Instant createdAt)
{}
