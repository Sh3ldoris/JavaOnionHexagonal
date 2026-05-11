package com.waterconnect.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.time.Instant;
import java.util.UUID;

import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.waterconnect.application.dto.CustomerResultDto;
import com.waterconnect.application.dto.enums.CustomerType;
import com.waterconnect.application.dto.mapper.CustomerResultDtoMapper;
import com.waterconnect.domain.model.aggregate.Customer;
import com.waterconnect.domain.model.valueobject.Address;
import com.waterconnect.domain.model.valueobject.ContactInfo;

@ExtendWith(MockitoExtension.class)
class CustomerResultDtoMapperTest {

    @Mock
    private Customer customer;

    private Address address;
    private ContactInfo contactInfo;
    private UUID customerId;
    private String fullName;
    private Instant registeredAt;

    @BeforeEach
    void setUp() {
        address = Instancio.create(Address.class);

        contactInfo = new ContactInfo(
                Instancio.gen().net().email().get(),
                Instancio.gen().string().digits().length(9).get(),
                address
        );

        customerId = UUID.randomUUID();
        fullName = Instancio.gen().string().get();
        registeredAt = Instant.now();

        when(customer.getCustomerId()).thenReturn(customerId);
        when(customer.getFullName()).thenReturn(fullName);
        when(customer.getCustomerType()).thenReturn(com.waterconnect.domain.model.enums.CustomerType.RESIDENTIAL);
        when(customer.getContactInfo()).thenReturn(contactInfo);
        when(customer.getRegisteredAt()).thenReturn(registeredAt);
    }

    // -------------------------------------------------------------------------
    // All scalar fields in one test
    // -------------------------------------------------------------------------
    @Test
    void fromDomain_shouldMapAllScalarFieldsCorrectly() {
        CustomerResultDto result = CustomerResultDtoMapper.fromDomain(customer);

        assertThat(result).isNotNull();
        assertThat(result).isInstanceOf(CustomerResultDto.class);

        assertThat(result.customerId()).isEqualTo(customerId);
        assertThat(result.fullName()).isEqualTo(fullName);
        assertThat(result.email()).isEqualTo(contactInfo.email());
        assertThat(result.phone()).isEqualTo(contactInfo.phone());
        assertThat(result.registeredAt()).isEqualTo(registeredAt);
    }

    // -------------------------------------------------------------------------
    // CustomerType — every enum value covered via @EnumSource
    // -------------------------------------------------------------------------
    @ParameterizedTest
    @EnumSource(com.waterconnect.domain.model.enums.CustomerType.class)
    void fromDomain_shouldMapEveryCustomerType(com.waterconnect.domain.model.enums.CustomerType domainType) {
        when(customer.getCustomerType()).thenReturn(domainType);

        CustomerResultDto result = CustomerResultDtoMapper.fromDomain(customer);

        assertThat(result.customerType())
                .isEqualTo(CustomerType.fromString(domainType.name()));
    }

    // -------------------------------------------------------------------------
    // Address delegation
    // -------------------------------------------------------------------------
    @Test
    void fromDomain_shouldMapAllAddressFieldsCorrectly() {
        CustomerResultDto result = CustomerResultDtoMapper.fromDomain(customer);

        assertThat(result.address()).satisfies(a -> {
            assertThat(a.street()).isEqualTo(address.street());
            assertThat(a.city()).isEqualTo(address.city());
            assertThat(a.postalCode()).isEqualTo(address.postalCode());
            assertThat(a.country()).isEqualTo(address.country());
        });
    }

    // -------------------------------------------------------------------------
    // Null / missing values
    // -------------------------------------------------------------------------
    @Nested
    class NullHandling {

        @Test
        void fromDomain_shouldAllowAddress() {
            when(customer.getContactInfo()).thenReturn(
                    new ContactInfo(
                            Instancio.gen().net().email().get(),
                            Instancio.gen().string().digits().length(9).get(),
                            null
                    )
            );

            CustomerResultDto result = CustomerResultDtoMapper.fromDomain(customer);

            assertThat(result).isNotNull();

            assertThat(result.phone()).isEqualTo(customer.getContactInfo().phone());
            assertThat(result.email()).isEqualTo(customer.getContactInfo().email());
            assertThat(result.address()).isNull();
        }

        @Test
        void fromDomain_shouldAllowNullContactInfo() {
            when(customer.getContactInfo()).thenReturn(null);

            CustomerResultDto result = CustomerResultDtoMapper.fromDomain(customer);

            assertThat(result).isNotNull();

            assertThat(result.phone()).isNull();
            assertThat(result.email()).isNull();
            assertThat(result.address()).isNull();
        }

        @Test
        void fromDomain_shouldAllowNullRegisteredAt() {
            when(customer.getRegisteredAt()).thenReturn(null);

            CustomerResultDto result = CustomerResultDtoMapper.fromDomain(customer);

            assertThat(result.registeredAt()).isNull();
        }
    }
}
