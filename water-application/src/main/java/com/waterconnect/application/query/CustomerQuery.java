package com.waterconnect.application.query;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.waterconnect.application.dto.CustomerResultDto;
import com.waterconnect.application.dto.Page;
import com.waterconnect.application.dto.mapper.CustomerResultDtoMapper;
import com.waterconnect.domain.exception.EntityNotFoundException;
import com.waterconnect.domain.port.outbound.CustomerRepository;

/**
 * Query service class for all the Customer domain queries
 */
@Service
public class CustomerQuery {
    private final CustomerRepository customerRepository;

    public CustomerQuery(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    /**
     * Get customer by provided Customer ID
     * @return Result DTO for customer
     * @throws EntityNotFoundException in a case no Customer founded
     */
    @Transactional
    public CustomerResultDto getById(UUID customerId) throws EntityNotFoundException {
        var customerOptional = customerRepository.findById(customerId);

        if (customerOptional.isEmpty()) {
            throw new EntityNotFoundException("Customer",  customerId);
        }

        return CustomerResultDtoMapper.fromDomain(customerOptional.get());
    }

    /**
     * Get customers list as page
     */
    @Transactional
    public Page<CustomerResultDto> getList(int page, int size) {
        List<CustomerResultDto> customers = customerRepository.findAll(page, size)
                .stream()
                .map(CustomerResultDtoMapper::fromDomain)
                .toList();

        return new Page<CustomerResultDto>(page, size, customers);
    }
}
