package com.tools.rental.service;

import com.tools.rental.domain.CreateCustomerRequest;
import com.tools.rental.exception.InvalidRequestException;
import com.tools.rental.exception.NotFoundException;
import com.tools.rental.model.Customer;
import com.tools.rental.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
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
    void createCustomer_invalidPhone() {
        CreateCustomerRequest request = new CreateCustomerRequest(null, "", "", null);
        var exception = assertThrows(InvalidRequestException.class, () -> customerService.createCustomer(request));

        assertThat(exception.getMessage(), is("Missing phone"));
    }

    @Test
    void createCustomer_invalidPhoneFormat() {
        CreateCustomerRequest request = new CreateCustomerRequest("12", "", "", null);
        var exception = assertThrows(InvalidRequestException.class, () -> customerService.createCustomer(request));

        assertThat(exception.getMessage(), is("Phone format"));
    }

    @Test
    void createCustomer_invalidFirstName() {
        CreateCustomerRequest request = new CreateCustomerRequest(PHONE, "", "", null);
        var exception = assertThrows(InvalidRequestException.class, () -> customerService.createCustomer(request));

        assertThat(exception.getMessage(), is("Missing first name"));
    }

    @Test
    void createCustomer_invalidLastName() {
        CreateCustomerRequest request = new CreateCustomerRequest(PHONE, "Bob", "", null);
        var exception = assertThrows(InvalidRequestException.class, () -> customerService.createCustomer(request));

        assertThat(exception.getMessage(), is("Missing last name"));
    }

    @Test
    void createCustomer_customerNotFound() {
        when(customerRepository.findByPhone(PHONE)).thenReturn(Optional.of(new Customer()));

        CreateCustomerRequest request = new CreateCustomerRequest(PHONE, "Bob", "Test", null);
        var exception = assertThrows(InvalidRequestException.class, () -> customerService.createCustomer(request));

        assertThat(exception.getMessage(), is("User exists."));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void getCustomerByPhone_invalidPhone(final String phone) {
        assertThrows(InvalidRequestException.class, () -> customerService.getCustomerByPhone(phone));
    }

    @Test
    void getCustomerByPhone_customerNotFound() {
        when(customerRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class,
                     () -> customerService.getCustomerByPhone(PHONE));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void deleteCustomerByPhone(final String phone) {
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