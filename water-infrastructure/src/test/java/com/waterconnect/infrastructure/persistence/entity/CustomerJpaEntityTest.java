package com.waterconnect.infrastructure.persistence.entity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.Instant;

import org.junit.jupiter.api.Test;

import com.waterconnect.domain.model.aggregate.Customer;
import com.waterconnect.domain.model.enums.CustomerType;
import com.waterconnect.domain.model.valueobject.Address;
import com.waterconnect.domain.model.valueobject.ContactInfo;

class CustomerJpaEntityTest {

    @Test
    void toDomain_rehydratesCustomerFromPersistedFields() {
        var contactInfo = new ContactInfo(
                "mapped@example.com",
                "+1-555-0101",
                new Address("Main Street 1", "Lisbon", "1000-001", "PT")
        );
        Customer original = Customer.register("Mapped Customer", CustomerType.COMMERCIAL, contactInfo);

        CustomerJpaEntity entity = CustomerJpaEntity.fromDomain(original);
        Customer restored = entity.toDomain();

        assertThat(restored.getCustomerId()).isEqualTo(original.getCustomerId());
        assertThat(restored.getFullName()).isEqualTo(original.getFullName());
        assertThat(restored.getCustomerType()).isEqualTo(original.getCustomerType());
        assertThat(restored.getRegisteredAt()).isEqualTo(original.getRegisteredAt());

        assertThat(restored.getContactInfo().email()).isEqualTo("mapped@example.com");
        assertThat(restored.getContactInfo().phone()).isEqualTo("+1-555-0101");
        assertThat(restored.getContactInfo().address().street()).isEqualTo("Main Street 1");
        assertThat(restored.getContactInfo().address().city()).isEqualTo("Lisbon");
        assertThat(restored.getContactInfo().address().postalCode()).isEqualTo("1000-001");
        assertThat(restored.getContactInfo().address().country()).isEqualTo("PT");
    }
}
