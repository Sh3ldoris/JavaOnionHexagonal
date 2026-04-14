package com.waterconnect.infrastructure.persistence.adapter;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.waterconnect.domain.model.aggregate.Customer;
import com.waterconnect.domain.port.outbound.CustomerRepository;
import com.waterconnect.infrastructure.persistence.repository.CustomerJpaRepository;

/**
 * Adapter: implements domain port using Spring Data JPA.
 *
 * TODO: Implement the mapping between domain and JPA entities.
 */
@Repository
public class CustomerRepositoryAdapter implements CustomerRepository {

    private final CustomerJpaRepository jpaRepository;

    public CustomerRepositoryAdapter(CustomerJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Customer save(Customer customer) {
        // TODO: CustomerJpaEntity entity = CustomerJpaEntity.fromDomain(customer);
        //       return jpaRepository.save(entity).toDomain();
        throw new UnsupportedOperationException("TODO: Implement save");
    }

    @Override
    public Optional<Customer> findById(UUID id) {
        // TODO: return jpaRepository.findById(id).map(CustomerJpaEntity::toDomain);
        throw new UnsupportedOperationException("TODO: Implement findById");
    }

    @Override
    public List<Customer> findAll(int page, int size) {
        // TODO: Use Pageable
        throw new UnsupportedOperationException("TODO: Implement findAll");
    }

    @Override
    public long count() {
        return jpaRepository.count();
    }
}
