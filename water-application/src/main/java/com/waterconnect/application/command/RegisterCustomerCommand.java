package com.waterconnect.application.command;

public record RegisterCustomerCommand(String fullName, String customerType,
                               String email, String phone, String street,
                               String city, String postalCode, String country) {
}
