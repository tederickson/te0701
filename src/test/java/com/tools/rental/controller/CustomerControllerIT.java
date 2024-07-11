package com.tools.rental.controller;

import com.tools.rental.client.RentalClient;
import com.tools.rental.domain.ChangePasswordRequest;
import com.tools.rental.domain.CreateCustomerRequest;
import com.tools.rental.domain.CustomerDigest;
import com.tools.rental.enumeration.CustomerStatus;
import com.tools.rental.exception.RentalServiceError;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.web.client.HttpClientErrorException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CustomerControllerIT {
    final private static String PHONE = "test phone";

    @LocalServerPort
    private int port;

    private RentalClient client;

    @BeforeEach
    void setUp() {
        client = new RentalClient("localhost", port);
    }

    @AfterEach
    void tearDown() {
        client.deleteCustomerByPhone(PHONE);
    }

    @Test
    void createCustomer() {
        CreateCustomerRequest request = new CreateCustomerRequest(PHONE, "first", "last", "email");
        CustomerDigest customer = client.createCustomer(request);

        validate(customer, "first", "last");
    }

    @Test
    void createCustomer_invalidPhone() {
        CreateCustomerRequest request = new CreateCustomerRequest("phone", "first", "last", "email");

        var exception = assertThrows(HttpClientErrorException.BadRequest.class, () -> client.createCustomer(request));
        assertThat(exception.getMessage(), containsString("Phone format"));
    }

    @Test
    void createCustomer_customerExist() {
        CreateCustomerRequest request = new CreateCustomerRequest("7195551234", "first", "last", "email");

        var exception = assertThrows(HttpClientErrorException.BadRequest.class, () -> client.createCustomer(request));
        assertThat(exception.getMessage(), containsString(RentalServiceError.EXISTING_USER.getMessage()));
    }

    @Test
    void getCustomerByPhone() {
        CreateCustomerRequest request = new CreateCustomerRequest(PHONE, "first1", "last1", "email");
        client.createCustomer(request);

        CustomerDigest customer = client.getCustomerByPhone(PHONE);

        validate(customer, "first1", "last1");
    }

    @Test
    void changePassword() {
        CreateCustomerRequest request = new CreateCustomerRequest(PHONE, "first", "last", "email");
        CustomerDigest customer = client.createCustomer(request);

        ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest("12AppleSauce!");
        client.changePassword(customer.getId(), changePasswordRequest);

        assertThat(client.getCustomerByPhone(PHONE).getStatus(), is(CustomerStatus.ACTIVE));
    }

    @Test
    void changePassword_customerNotFound() {
        ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest("12AppleSauce!");

        assertThrows(HttpClientErrorException.NotFound.class,
                     () -> client.changePassword(-1, changePasswordRequest));
    }

    private void validate(CustomerDigest customer, String first, String last) {
        assertThat(customer.getCreateDate(), is(notNullValue()));
        assertThat(customer.getId(), is(notNullValue()));
        assertThat(customer.getPhone(), is(PHONE));
        assertThat(customer.getFirstName(), is(first));
        assertThat(customer.getLastName(), is(last));
        assertThat(customer.getStatus(), is(CustomerStatus.NEW));
        assertThat(customer.getEmail(), is("email"));
    }
}