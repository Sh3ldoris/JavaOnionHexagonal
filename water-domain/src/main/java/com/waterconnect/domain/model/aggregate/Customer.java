package com.waterconnect.domain.model.aggregate;

import com.waterconnect.domain.event.CustomerRegisteredEvent;
import com.waterconnect.domain.model.enums.CustomerType;
import com.waterconnect.domain.model.valueobject.ContactInfo;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Customer Aggregate Root.
 */
public class Customer {

    private UUID customerId;
    private String fullName;
    private CustomerType customerType;
    private ContactInfo contactInfo;
    private Instant registeredAt;

    private final List<CustomerRegisteredEvent> customerRegisteredEvents = new ArrayList<>();

    protected Customer() {}

    /**
     * Change the current contact info for the one passed
     */
    public void updateContactInfo(ContactInfo contactInfoToUpdate) {
        this.contactInfo = contactInfoToUpdate;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public String getFullName() {
        return fullName;
    }

    public CustomerType getCustomerType() {
        return customerType;
    }

    public ContactInfo getContactInfo() {
        return contactInfo;
    }

    public Instant getRegisteredAt() {
        return registeredAt;
    }

    public List<CustomerRegisteredEvent> getCustomerRegisteredEvents() {
        return Collections.unmodifiableList(this.customerRegisteredEvents);
    }

    /**
     * Customer factory
     */
    public static Customer register(String fullName, CustomerType customerType, ContactInfo contactInfo) {
        // Validation of inputs
        Objects.requireNonNull(fullName, "fullName must not be null");
        Objects.requireNonNull(customerType, "customerType must not be null");

        if (fullName.isBlank()) {
            throw new IllegalArgumentException("fullName must not be blank");
        }

        // Create a new domain object
        var customer = new Customer();

        // Set attributes from parameters
        customer.fullName = fullName;
        customer.customerType = customerType;
        customer.contactInfo = contactInfo;

        // Set other attributes
        final var id = UUID.randomUUID();
        final var now = Instant.now();
        customer.customerId = id;
        customer.registeredAt = now;

        // Register domain event
        customer.customerRegisteredEvents.add(new CustomerRegisteredEvent(UUID.randomUUID(), now, id, fullName));

        return customer;
    }

    protected void setCustomerId(UUID customerId) {
        this.customerId = customerId;
    }

    protected void setFullName(String fullName) {
        this.fullName = fullName;
    }

    protected void setCustomerType(CustomerType customerType) {
        this.customerType = customerType;
    }

    protected void setContactInfo(ContactInfo contactInfo) {
        this.contactInfo = contactInfo;
    }

    protected void setRegisteredAt(Instant registeredAt) {
        this.registeredAt = registeredAt;
    }
}
