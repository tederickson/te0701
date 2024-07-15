package com.tools.rental.model;

import com.tools.rental.enumeration.ToolCode;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.Test;

import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanConstructor;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class StoreToolRentalLedgerTest {
    private static final long ID = 123L;
    private static final StoreToolRentalLedger STORE_TOOL_RENTAL =
            new StoreToolRentalLedger().setId(ID)
                    .setStoreId((short) 1)
                    .setToolCode(ToolCode.JAKD);

    @Test
    void shouldHaveNoArgsConstructor() {
        assertThat(StoreToolRentalLedger.class, hasValidBeanConstructor());
    }

    @Test
    void equalsAndHashCode() {
        EqualsVerifier.simple().forClass(StoreToolRentalLedger.class)
                .suppress(Warning.NONFINAL_FIELDS)
                .suppress(Warning.SURROGATE_KEY)
                .verify();
    }

    @Test
    void testToString() {
        assertNotNull(STORE_TOOL_RENTAL.toString());
    }
}