package com.waterconnect.domain.model.enums;

public enum CustomerType {
    RESIDENTIAL, COMMERCIAL, INDUSTRIAL;

    public static CustomerType fromString(String value) {
        try {
            return CustomerType.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("cannot convert " + value + " to CustomerType", e);
        }
    }
}
