package com.tools.rental.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CustomerTest {
    private static final long ID = 123L;
    private static final Customer customer = new Customer().setId(ID).setPhone("12321");
    private static final Customer otherCustomer = new Customer().setId(ID).setPassword("password");

    @Test
    void testEquals() {
        assertEquals(customer, otherCustomer);
        assertEquals(customer, customer);
        assertFalse(customer.equals("a"));
        assertFalse(customer.equals(null));
        assertNotEquals(customer, new Customer());
    }

    @Test
    void testHashCode() {
        assertEquals(customer.hashCode(), otherCustomer.hashCode());
    }

    @Test
    void getPassword() {
        assertEquals("password", otherCustomer.getPassword());
    }

    @Test
    void testToString() {
        assertNotNull(customer.toString());
    }
}