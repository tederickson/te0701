package com.tools.rental.model;

import com.tools.rental.enumeration.ToolType;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.Test;

import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanConstructor;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class StoreToolTypeChargeTest {
    private static final StoreToolTypeCharge CHARGE =
            new StoreToolTypeCharge().setId(11L).setToolType(ToolType.CHAINSAW);

    @Test
    void shouldHaveNoArgsConstructor() {
        assertThat(StoreToolTypeCharge.class, hasValidBeanConstructor());
    }

    @Test
    void equalsAndHashCode() {
        EqualsVerifier.simple().forClass(StoreToolTypeCharge.class)
                .suppress(Warning.NONFINAL_FIELDS)
                .suppress(Warning.SURROGATE_KEY)
                .verify();
    }

    @Test
    void testToString() {
        assertNotNull(CHARGE.toString());
    }
}