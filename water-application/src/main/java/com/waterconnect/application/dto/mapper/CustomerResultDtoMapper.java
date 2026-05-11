package com.waterconnect.application.dto.mapper;

import java.util.Objects;

import com.waterconnect.application.dto.CustomerResultDto;
import com.waterconnect.application.dto.enums.CustomerType;
import com.waterconnect.domain.model.aggregate.Customer;

public class CustomerResultDtoMapper {
    private CustomerResultDtoMapper() {}

    public static CustomerResultDto fromDomain(Customer customer) {
        if (Objects.isNull(customer)) {
            return null;
        }

        var contactInfo = customer.getContactInfo();
        var isContactInfo = Objects.nonNull(contactInfo);

        var addressResult = AddressResultDtoMapper.fromDomain(
                isContactInfo ? contactInfo.address() : null
        );

        return new CustomerResultDto(
                customer.getCustomerId(),
                customer.getFullName(),
                CustomerType.fromString(customer.getCustomerType().name()),
                isContactInfo ? contactInfo.email() : null,
                isContactInfo ? contactInfo.phone() : null,
                addressResult,
                customer.getRegisteredAt()
        );
    }
}
