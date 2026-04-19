package com.waterconnect.application.dto.mapper;

import com.waterconnect.application.dto.CustomerResultDto;
import com.waterconnect.application.dto.enums.CustomerType;
import com.waterconnect.domain.model.aggregate.Customer;

public class CustomerResultDtoMapper {
    private CustomerResultDtoMapper() {}

    public static CustomerResultDto fromDomain(Customer customer) {
        var contactInfo = customer.getContactInfo();
        var address = contactInfo.address();

        var addressResult = AddressResultDtoMapper.fromDomain(address);

        return new CustomerResultDto(customer.getCustomerId(), customer.getFullName(),
                CustomerType.fromString(customer.getCustomerType().name()), contactInfo.email(),
                contactInfo.phone(), addressResult, customer.getRegisteredAt());
    }
}
