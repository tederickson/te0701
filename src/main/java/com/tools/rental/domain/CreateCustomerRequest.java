package com.tools.rental.domain;

public record CreateCustomerRequest(String phone, String firstName, String lastName, String email, String password) {
}
