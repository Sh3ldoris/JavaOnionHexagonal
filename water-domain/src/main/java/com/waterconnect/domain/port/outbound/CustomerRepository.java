package com.waterconnect.domain.port.outbound;

import java.util.Optional;
import java.util.UUID;

import com.waterconnect.domain.model.aggregate.Customer;

/**
 * Outbound port for Customer persistence.
 * Implemented by infrastructure adapters.
 */
public interface CustomerRepository {
    Customer save(Customer customer);
    Optional<Customer> findById(UUID id);
    java.util.List<Customer> findAll(int page, int size);
    long count();
}
