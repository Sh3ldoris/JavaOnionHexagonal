package com.waterconnect.application.dto;

public record AddressResultDto(
        String street,
        String city,
        String postalCode,
        String country
) {
}
