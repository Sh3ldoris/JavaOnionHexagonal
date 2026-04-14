package com.waterconnect.infrastructure.persistence.adapter;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import com.waterconnect.domain.model.aggregate.Customer;
import com.waterconnect.domain.port.outbound.CustomerRepository;
import com.waterconnect.infrastructure.persistence.entity.CustomerJpaEntity;
import com.waterconnect.infrastructure.persistence.repository.CustomerJpaRepository;

/**
 * Adapter: implements domain port using Spring Data JPA.
 */
@Repository
public class CustomerRepositoryAdapter implements CustomerRepository {

    private final CustomerJpaRepository jpaRepository;

    public CustomerRepositoryAdapter(CustomerJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Customer save(Customer customer) {
        // Create entity object from domain
        var entity = CustomerJpaEntity.fromDomain(customer);
        // Save the entity to the repository
        return this.jpaRepository.save(entity).toDomain();
    }

    @Override
    public Optional<Customer> findById(UUID id) {
        return this.jpaRepository.findById(id)
                .map(CustomerJpaEntity::toDomain);
    }

    @Override
    public List<Customer> findAll(int page, int size) {
        return this.jpaRepository.findAll(PageRequest.of(page, size))
                .stream()
                .map(CustomerJpaEntity::toDomain)
                .toList();
    }

    @Override
    public long count() {
        return jpaRepository.count();
    }
}
