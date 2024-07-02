package com.tools.rental.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PasswordEncodeDecodeTest {
    @Test
    void matches() {
        final var password = "Zimbabwe213!@#$%^&*()";
        final var encodedPassword = PasswordEncodeDecode.encode(password);

        assertNotEquals(password, encodedPassword);
        assertTrue(PasswordEncodeDecode.matches(password, encodedPassword));
    }
}