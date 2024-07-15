package com.tools.rental.model;

import com.tools.rental.enumeration.ToolCode;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.Test;

import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanConstructor;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class StoreToolInventoryTest {
    private static final StoreToolInventory STORE_TOOL_INVENTORY =
            new StoreToolInventory().setId(123L)
                    .setStoreId((short) 1)
                    .setToolCode(ToolCode.JAKD)
                    .setMaxAvailable((short) 4);

    @Test
    void shouldHaveNoArgsConstructor() {
        assertThat(StoreToolInventory.class, hasValidBeanConstructor());
    }

    @Test
    void equalsAndHashCode() {
        EqualsVerifier.simple().forClass(StoreToolInventory.class)
                .suppress(Warning.NONFINAL_FIELDS)
                .suppress(Warning.SURROGATE_KEY)
                .verify();
    }

    @Test
    void testToString() {
        assertNotNull(STORE_TOOL_INVENTORY.toString());
    }
}