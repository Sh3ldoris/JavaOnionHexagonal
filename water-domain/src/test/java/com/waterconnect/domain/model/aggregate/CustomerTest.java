package com.waterconnect.domain.model.aggregate;

import static com.waterconnect.domain.model.aggregate.support.AggregateTestFixtures.contactInfo;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.Instant;

import org.junit.jupiter.api.Test;

import com.waterconnect.domain.model.enums.CustomerType;
import com.waterconnect.domain.model.valueobject.Address;
import com.waterconnect.domain.model.valueobject.ContactInfo;

class CustomerTest {

    @Test
    void register_setsIdentityFieldsAndRegisteredAt() {
        Instant before = Instant.now();
        var info = contactInfo("user@example.com");
        var customer = Customer.register("Jane Doe", CustomerType.RESIDENTIAL, info);
        Instant after = Instant.now();

        assertThat(customer.getCustomerId()).isNotNull();
        assertThat(customer.getFullName()).isEqualTo("Jane Doe");
        assertThat(customer.getCustomerType()).isEqualTo(CustomerType.RESIDENTIAL);
        assertThat(customer.getContactInfo()).isEqualTo(info);
        assertThat(customer.getRegisteredAt()).isNotNull();
        assertThat(customer.getRegisteredAt()).isAfterOrEqualTo(before);
        assertThat(customer.getRegisteredAt()).isBeforeOrEqualTo(after);
    }

    @Test
    void register_recordsSingleCustomerRegisteredEvent_alignedWithAggregate() {
        var info = contactInfo("corp@example.com");
        var customer = Customer.register("Acme Ltd", CustomerType.COMMERCIAL, info);

        assertThat(customer.getCustomerRegisteredEvents()).hasSize(1);
        var event = customer.getCustomerRegisteredEvents().getFirst();
        assertThat(event.eventId()).isNotNull();
        assertThat(event.customerName()).isEqualTo("Acme Ltd");
        assertThat(event.customerId()).isEqualTo(customer.getCustomerId());
        assertThat(event.occurredAt()).isEqualTo(customer.getRegisteredAt());
    }

    @Test
    void updateContactInfo_replacesContactInfo() {
        var original = contactInfo("first@example.com");
        var customer = Customer.register("Pat", CustomerType.INDUSTRIAL, original);
        var updated = contactInfo("second@example.com");

        customer.updateContactInfo(updated);

        assertThat(customer.getContactInfo()).isEqualTo(updated);
    }

    @Test
    void register_acceptsNullContactInfo_currentApi() {
        var customer = Customer.register("No Contact", CustomerType.RESIDENTIAL, null);
        assertThat(customer.getContactInfo()).isNull();
    }

    @Test
    void contactInfo_rejectsInvalidEmail() {
        var address = new Address("St", "City", "PC", "US");
        assertThatThrownBy(() -> new ContactInfo("not-an-email", "+1", address))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("email");
    }
}
