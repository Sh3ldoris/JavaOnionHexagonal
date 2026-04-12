package com.waterconnect.application.usecase;

import com.waterconnect.domain.port.outbound.CustomerRepository;
import com.waterconnect.domain.port.outbound.DomainEventPublisher;

/**
 * Use case: Register a new customer.
 * TODO: Implement
 * 1. Create a command record (inner class or separate file):
 *    record RegisterCustomerCommand(String fullName, String customerType,
 *        String email, String phone, String street, String city, String postalCode, String country)
 * 2. Inject CustomerRepository and DomainEventPublisher via constructor
 * 3. Implement execute(RegisterCustomerCommand cmd):
 *    - Build ContactInfo and Address value objects
 *    - Call Customer.register(...) factory
 *    - Save via repository
 *    - Publish CustomerRegisteredEvent
 *    - Return the saved customer (or a result DTO)
 * 4. Annotate class with @Service, method with @Transactional
 */
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

    // TODO: Add command record and execute() method
}
