package com.waterconnect.application.usecase;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.waterconnect.application.command.RegisterCustomerCommand;
import com.waterconnect.domain.event.DomainEvent;
import com.waterconnect.domain.model.aggregate.Customer;
import com.waterconnect.domain.model.enums.CustomerType;
import com.waterconnect.domain.model.valueobject.Address;
import com.waterconnect.domain.model.valueobject.ContactInfo;
import com.waterconnect.domain.port.outbound.CustomerRepository;
import com.waterconnect.domain.port.outbound.DomainEventPublisher;

/**
 * Use case: Register a new customer.
 */
@Service
public class RegisterCustomerUseCase {

    private final CustomerRepository customerRepository;
    private final DomainEventPublisher eventPublisher;

    public RegisterCustomerUseCase(
            CustomerRepository customerRepository,
            DomainEventPublisher eventPublisher
    ) {
        this.customerRepository = customerRepository;
        this.eventPublisher = eventPublisher;
    }

    @Transactional
    public UUID execute(RegisterCustomerCommand command) {
        var address = new Address(command.street(), command.city(), command.postalCode(), command.country());
        var contactInfo = new ContactInfo(command.email(), command.phone(), address);

        // Create a new customer
        var newCustomer = Customer.register(command.fullName(),
                CustomerType.fromString(command.customerType()), contactInfo);

        // Save the new customer
        this.customerRepository.save(newCustomer);
        // Publish customer domain events
        for (DomainEvent event : newCustomer.getCustomerRegisteredEvents()) {
            this.eventPublisher.publish(event);
        }

        return newCustomer.getCustomerId();
    }
}
