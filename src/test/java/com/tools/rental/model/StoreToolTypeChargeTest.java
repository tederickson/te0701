package com.tools.rental.model;

import com.tools.rental.enumeration.ToolType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class StoreToolTypeChargeTest {
    private static final long ID = 123L;
    private static final StoreToolTypeCharge CHARGE =
            new StoreToolTypeCharge().setId(ID).setToolType(ToolType.CHAINSAW);
    private static final StoreToolTypeCharge OTHER_CHARGE = new StoreToolTypeCharge().setId(ID);

    @Test
    void testEquals() {
        assertEquals(CHARGE, OTHER_CHARGE);
        assertEquals(CHARGE, CHARGE);
        assertFalse(CHARGE.equals("a"));
        assertFalse(CHARGE.equals(null));
        assertNotEquals(CHARGE, new StoreToolTypeCharge());
    }

    @Test
    void testHashCode() {
        assertEquals(CHARGE.hashCode(), OTHER_CHARGE.hashCode());
    }

    @Test
    void testToString() {
        assertNotNull(CHARGE.toString());
    }
}