package com.tools.rental.model;

import com.tools.rental.enumeration.ToolCode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class StoreToolInventoryTest {
    private static final long ID = 123L;
    private static final StoreToolInventory STORE_TOOL_INVENTORY =
            new StoreToolInventory().setId(ID).setStoreId((short) 1)
                    .setToolCode(ToolCode.JAKD)
                    .setMaxAvailable((short) 4);
    private static final StoreToolInventory OTHER_STORE_TOOL_INVENTORY = new StoreToolInventory().setId(ID);

    @Test
    void testEquals() {
        assertEquals(STORE_TOOL_INVENTORY, OTHER_STORE_TOOL_INVENTORY);
        assertEquals(STORE_TOOL_INVENTORY, STORE_TOOL_INVENTORY);
        assertFalse(STORE_TOOL_INVENTORY.equals("a"));
        assertFalse(STORE_TOOL_INVENTORY.equals(null));
        assertNotEquals(STORE_TOOL_INVENTORY, new StoreToolInventory());
    }

    @Test
    void testHashCode() {
        assertEquals(STORE_TOOL_INVENTORY.hashCode(), OTHER_STORE_TOOL_INVENTORY.hashCode());
    }

    @Test
    void testToString() {
        assertNotNull(STORE_TOOL_INVENTORY.toString());
    }
}