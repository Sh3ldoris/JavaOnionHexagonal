package com.waterconnect.domain.model.valueobject;

public record Address(
        String street,
        String city,
        String postalCode,
        String country
) {}
