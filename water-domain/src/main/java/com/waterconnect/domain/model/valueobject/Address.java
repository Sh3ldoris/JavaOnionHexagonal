package com.waterconnect.domain.model.valueobject;

import java.util.Objects;

public record Address(
        String street,
        String city,
        String postalCode,
        String country
) {
    public Address {
        Objects.requireNonNull(street, "street must not be null");
        Objects.requireNonNull(city, "city must not be null");
        Objects.requireNonNull(postalCode, "postalCode must not be null");
        Objects.requireNonNull(country, "country must not be null");
    }
}
