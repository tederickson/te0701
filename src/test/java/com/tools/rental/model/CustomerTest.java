package com.tools.rental.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CustomerTest {
    private static final long ID = 123L;
    private static final Customer CUSTOMER = new Customer().setId(ID).setPhone("12321");
    private static final Customer OTHER_CUSTOMER = new Customer().setId(ID).setPassword("password");

    @Test
    void testEquals() {
        assertEquals(CUSTOMER, OTHER_CUSTOMER);
        assertEquals(CUSTOMER, CUSTOMER);
        assertFalse(CUSTOMER.equals("a"));
        assertFalse(CUSTOMER.equals(null));
        assertNotEquals(CUSTOMER, new Customer());
    }

    @Test
    void testHashCode() {
        assertEquals(CUSTOMER.hashCode(), OTHER_CUSTOMER.hashCode());
    }

    @Test
    void getPassword() {
        assertEquals("password", OTHER_CUSTOMER.getPassword());
    }

    @Test
    void testToString() {
        assertNotNull(CUSTOMER.toString());
    }
}