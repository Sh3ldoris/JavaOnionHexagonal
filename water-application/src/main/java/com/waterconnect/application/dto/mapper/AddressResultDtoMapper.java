package com.waterconnect.application.dto.mapper;

import com.waterconnect.application.dto.AddressResultDto;
import com.waterconnect.domain.model.valueobject.Address;

public class AddressResultDtoMapper {
    private AddressResultDtoMapper() {}

    public static AddressResultDto fromDomain(Address address) {
        if (address == null) {
            return null;
        }

        return new AddressResultDto(address.street(), address.city(), address.postalCode(), address.country());
    }
}
