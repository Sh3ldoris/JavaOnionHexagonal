package com.waterconnect.application.dto.enums;

public enum WorkOrderType {
    NEW_CONNECTION, REPAIR, METER_REPLACEMENT, DISCONNECTION;

    public static WorkOrderType fromString(String value) {
        try {
            return valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("cannot convert " + value + " to WorkOrderType", e);
        }
    }
}
