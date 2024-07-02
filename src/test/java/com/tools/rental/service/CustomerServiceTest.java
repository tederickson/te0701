package com.tools.rental.service;

import com.tools.rental.exception.InvalidRequestException;
import com.tools.rental.exception.NotFoundException;
import com.tools.rental.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CustomerServiceTest {
    private static final String PHONE = "8015551234";

    private CustomerRepository customerRepository;

    private CustomerService customerService;

    @BeforeEach
    void setUp() {
        customerRepository = mock(CustomerRepository.class);
        customerService = new CustomerService(customerRepository);
    }

    @Test
    void createCustomer() {
    }

    @Test
    void getCustomerByPhone() {
    }

    @ParameterizedTest
    @NullAndEmptySource
    void deleteCustomerByPhone(String phone) {
        assertThrows(InvalidRequestException.class, () -> customerService.deleteCustomerByPhone(phone));
    }

    @Test
    void changePassword_invalidPassword() {
        assertThrows(InvalidRequestException.class, () -> customerService.changePassword(1L, "password"));
    }

    @Test
    void changePassword_customerNotFound() {
        when(customerRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class,
                     () -> customerService.changePassword(1L, "Zimbabwe213!"));
    }
}