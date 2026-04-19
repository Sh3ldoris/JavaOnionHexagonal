package com.waterconnect.application.dto.enums;

public enum ConnectorType {
    TEE, ELBOW, COUPLING, VALVE, REDUCER;

    public static ConnectorType fromString(String value) {
        try {
            return valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("cannot convert " + value + " to ConnectorType", e);
        }
    }
}
