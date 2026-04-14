package com.waterconnect.infrastructure.persistence.entity;

import java.time.Instant;
import java.util.UUID;

import com.waterconnect.domain.model.aggregate.Customer;
import com.waterconnect.domain.model.enums.CustomerType;
import com.waterconnect.domain.model.valueobject.Address;
import com.waterconnect.domain.model.valueobject.ContactInfo;
import com.waterconnect.infrastructure.persistence.entity.factory.CustomerFactory;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * JPA entity for Customer. Maps to the database table.
 */
@Entity
@Table(name = "customers")
public class CustomerJpaEntity {

    @Id
    private UUID customerId;

    private String fullName;

    @Enumerated(EnumType.STRING)
    private CustomerType customerType;

    private String email;
    private String phone;
    private String street;
    private String city;
    private String postalCode;
    private String country;

    private Instant registeredAt;

    // JPA requires no-arg constructor
    protected CustomerJpaEntity() {}

    /**
     * Factory from Customer domain object
     */
    public static CustomerJpaEntity fromDomain(Customer customer) {
        var entity = new CustomerJpaEntity();

        entity.customerId = customer.getCustomerId();
        entity.fullName = customer.getFullName();
        entity.customerType = customer.getCustomerType();

        ContactInfo contactInfo = customer.getContactInfo();
        entity.email = contactInfo.email();
        entity.phone = contactInfo.phone();

        Address address = contactInfo.address();
        entity.street = address.street();
        entity.city = address.city();
        entity.postalCode = address.postalCode();
        entity.country = address.country();

        entity.registeredAt = customer.getRegisteredAt();

        return entity;
    }

    /**
     * Factory of Customer domain object
     */
    public Customer toDomain() {
        var type = customerType;
        var address = new Address(street, city, postalCode, country);
        var contactInfo = new ContactInfo(email, phone, address);

        return CustomerFactory.createCustomer(
                customerId,
                fullName,
                type,
                contactInfo,
                registeredAt
        );
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public void setCustomerId(UUID customerId) {
        this.customerId = customerId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public CustomerType getCustomerType() {
        return customerType;
    }

    public void setCustomerType(CustomerType customerType) {
        this.customerType = customerType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Instant getRegisteredAt() {
        return registeredAt;
    }

    public void setRegisteredAt(Instant registeredAt) {
        this.registeredAt = registeredAt;
    }
}
