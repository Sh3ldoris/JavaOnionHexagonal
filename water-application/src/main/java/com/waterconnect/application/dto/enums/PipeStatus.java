package com.waterconnect.application.dto.enums;

public enum PipeStatus {
    PLANNED, INSTALLED, ACTIVE, DECOMMISSIONED;

    public static PipeStatus fromString(String value) {
        try {
            return valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("cannot convert " + value + " to PipeStatus", e);
        }
    }
}
