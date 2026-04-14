package com.waterconnect.infrastructure.persistence.entity.factory;

import com.waterconnect.domain.model.aggregate.Customer;
import com.waterconnect.domain.model.enums.CustomerType;
import com.waterconnect.domain.model.valueobject.ContactInfo;

import java.time.Instant;
import java.util.UUID;

public class CustomerFactory extends Customer {

    private CustomerFactory() {}

    public static Customer createCustomer(
            UUID customerId,
            String fullName,
            CustomerType customerType,
            ContactInfo contactInfo,
            Instant registeredAt
    ) {
        var customer = new CustomerFactory();
        customer.setCustomerId(customerId);
        customer.setFullName(fullName);
        customer.setCustomerType(customerType);
        customer.setContactInfo(contactInfo);
        customer.setRegisteredAt(registeredAt);
        return customer;
    }
}
