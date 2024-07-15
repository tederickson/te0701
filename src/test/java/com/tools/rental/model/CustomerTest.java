package com.tools.rental.model;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.Test;

import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanConstructor;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CustomerTest {
    private static final Customer CUSTOMER = new Customer().setId(123L).setPhone("12321").setPassword("password");

    @Test
    void shouldHaveNoArgsConstructor() {
        assertThat(Customer.class, hasValidBeanConstructor());
    }

    @Test
    void equalsAndHashCode() {
        EqualsVerifier.simple().forClass(Customer.class)
                .suppress(Warning.NONFINAL_FIELDS)
                .suppress(Warning.SURROGATE_KEY)
                .verify();
    }

    @Test
    void testToString() {
        assertNotNull(CUSTOMER.toString());
    }
}