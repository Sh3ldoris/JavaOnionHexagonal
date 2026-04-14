package com.waterconnect.infrastructure.persistence.entity;

import java.time.Instant;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * JPA entity for Customer. Maps to the database table.
 *
 * TODO: Complete the mapping.
 * - This is the ONLY place where JPA annotations appear for Customer
 * - You'll need a mapper to convert between this and the domain Customer aggregate
 */
@Entity
@Table(name = "customers")
public class CustomerJpaEntity {

    @Id
    private UUID customerId;

    private String fullName;

    @Enumerated(EnumType.STRING)
    private String customerType;

    private String email;
    private String phone;
    private String street;
    private String city;
    private String postalCode;
    private String country;

    private Instant registeredAt;

    // JPA requires no-arg constructor
    protected CustomerJpaEntity() {}

    // TODO: Add getters, setters, and static mapping methods:
    //   static CustomerJpaEntity fromDomain(Customer customer)
    //   Customer toDomain()
}
