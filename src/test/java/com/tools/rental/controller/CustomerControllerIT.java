package com.tools.rental.controller;

import com.tools.rental.client.RentalClient;
import com.tools.rental.domain.ChangePasswordRequest;
import com.tools.rental.domain.CreateCustomerRequest;
import com.tools.rental.domain.CustomerDigest;
import com.tools.rental.enumeration.CustomerStatus;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CustomerControllerIT {
    final private String phone = "test phone";

    @LocalServerPort
    private int port;

    private RentalClient client;

    @BeforeEach
    void setUp() {
        client = new RentalClient("localhost", port);
    }

    @AfterEach
    void tearDown() {
        client.deleteCustomerByPhone(phone);
    }

    @Test
    void createCustomer() {
        CreateCustomerRequest request = new CreateCustomerRequest(phone, "first", "last");
        CustomerDigest customer = client.createCustomer(request);

        validate(customer, "first", "last");
    }

    @Test
    void getCustomerByPhone() {
        CreateCustomerRequest request = new CreateCustomerRequest(phone, "first1", "last1");
        client.createCustomer(request);

        CustomerDigest customer = client.getCustomerByPhone(phone);

        validate(customer, "first1", "last1");
    }

    @Test
    void changePassword() {
        CreateCustomerRequest request = new CreateCustomerRequest(phone, "first", "last");
        CustomerDigest customer = client.createCustomer(request);

        ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest("12AppleSauce!");
        client.changePassword(customer.getId(), changePasswordRequest);

        assertThat(client.getCustomerByPhone(phone).getStatus(), is(CustomerStatus.ACTIVE));
    }

    private void validate(CustomerDigest customer, String first, String last) {
        assertThat(customer.getCreateDate(), is(notNullValue()));
        assertThat(customer.getId(), is(notNullValue()));
        assertThat(customer.getPhone(), is(phone));
        assertThat(customer.getFirstName(), is(first));
        assertThat(customer.getLastName(), is(last));
        assertThat(customer.getStatus(), is(CustomerStatus.NEW));
    }
}