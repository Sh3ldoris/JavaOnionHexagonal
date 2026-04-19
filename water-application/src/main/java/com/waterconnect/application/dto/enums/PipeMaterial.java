package com.waterconnect.application.dto.enums;

public enum PipeMaterial {
    PVC, COPPER, STEEL, HDPE;

    public static PipeMaterial fromString(String value) {
        try {
            return valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("cannot convert " + value + " to PipeMaterial", e);
        }
    }
}
