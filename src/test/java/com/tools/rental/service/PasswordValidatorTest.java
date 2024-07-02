package com.tools.rental.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PasswordValidatorTest {

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"apple", "just  kidding", "2001is_long_gone"})
    void isNotValid(String password) {
        assertTrue(PasswordValidator.isNotValid(password));
    }

    @Test
    void isValid() {
        assertFalse(PasswordValidator.isNotValid("SuperCaliTest2023!"));
    }
}