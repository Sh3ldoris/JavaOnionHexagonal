package com.waterconnect.domain.model.aggregate;

import com.waterconnect.domain.event.CustomerRegisteredEvent;
import com.waterconnect.domain.model.enums.CustomerType;
import com.waterconnect.domain.model.valueobject.ContactInfo;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
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
        return customerRegisteredEvents;
    }

    /**
     * Customer factory
     */
    public static Customer register(String fullName, CustomerType customerType, ContactInfo contactInfo) {
        // Create a new domain object
        var customer = new Customer();

        // Set attributes from parameters
        customer.fullName = fullName;
        customer.customerType = customerType;
        customer.contactInfo = contactInfo;

        // Set other attributes
        final var id = UUID.randomUUID();
        final var now = Instant.now();
        customer.customerId = UUID.randomUUID();
        customer.registeredAt = now;

        // Register domain event
        customer.customerRegisteredEvents.add(new CustomerRegisteredEvent(UUID.randomUUID(), now, id, fullName));

        return customer;
    }
}
