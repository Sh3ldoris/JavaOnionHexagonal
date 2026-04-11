package com.waterconnect.domain.model.valueobject;

import java.util.Objects;

public record ContactInfo(
        String email,
        String phone,
        Address address
) {
    public ContactInfo {
        Objects.requireNonNull(email, "Email must not be null");
        if (!email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
            throw new IllegalArgumentException("Invalid email format: " + email);
        }
    }
}
