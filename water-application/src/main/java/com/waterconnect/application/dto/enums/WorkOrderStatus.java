package com.waterconnect.application.dto.enums;

public enum WorkOrderStatus {
    CREATED, SCHEDULED, IN_PROGRESS, COMPLETED, CANCELLED;

    public static WorkOrderStatus fromString(String value) {
        try {
            return valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("cannot convert " + value + " to WorkOrderStatus", e);
        }
    }
}
