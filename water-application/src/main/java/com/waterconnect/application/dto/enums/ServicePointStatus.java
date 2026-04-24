package com.waterconnect.application.dto.enums;

public enum ServicePointStatus {
    REQUESTED, APPROVED, INSTALLED, ACTIVE, SUSPENDED, DISCONNECTED;

    public static ServicePointStatus fromString(String value) {
        try {
            return valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("cannot convert " + value + " to ServicePointStatus", e);
        }
    }
}
