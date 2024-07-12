package com.tools.rental.model;

import com.tools.rental.enumeration.ToolCode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class StoreToolRentalTest {
    private static final long ID = 123L;
    private static final StoreToolRental STORE_TOOL_RENTAL =
            new StoreToolRental().setId(ID).setStoreId((short) 1)
                    .setToolCode(ToolCode.JAKD);
    private static final StoreToolRental OTHER_STORE_TOOL_RENTAL = new StoreToolRental().setId(ID);

    @Test
    void testEquals() {
        assertEquals(STORE_TOOL_RENTAL, OTHER_STORE_TOOL_RENTAL);
        assertEquals(STORE_TOOL_RENTAL, STORE_TOOL_RENTAL);
        assertFalse(STORE_TOOL_RENTAL.equals("a"));
        assertFalse(STORE_TOOL_RENTAL.equals(null));
        assertNotEquals(STORE_TOOL_RENTAL, new StoreToolRental());
    }

    @Test
    void testHashCode() {
        assertEquals(STORE_TOOL_RENTAL.hashCode(), OTHER_STORE_TOOL_RENTAL.hashCode());
    }

    @Test
    void testToString() {
        assertNotNull(STORE_TOOL_RENTAL.toString());
    }
}